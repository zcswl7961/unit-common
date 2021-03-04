package com.zcswl.flink.watermarks;

import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;

/**
 * 每隔五秒，将过去是10秒内，通话时间最长的通话日志输出。
 * @author zhoucg
 * @date 2021-03-03 22:43
 */
public class WaterMarkDemo {

    public static void main(String[] args) {
        // 得到flink流式处理的运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置基于事件时间语义的流处理
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // 设置对应的并行度
        env.setParallelism(1);
        // 设置周期的产生watermark的时间间隔，当数据流很大的时候，如果每个事件都产生水位线，影响性能
        env.getConfig().setAutoWatermarkInterval(100);

        // 通过socker获取流
        DataStream<String> dataStreamSource = env.socketTextStream("192.168.129.128", 8888);
    
        // 流处理
        dataStreamSource
                .flatMap((FlatMapFunction<String, StationLog>) (value, out) -> {
            String[] words = value.split(",");
            out.collect(new StationLog(words[0], words[1],words[2], Long.parseLong(words[3]), Long.parseLong(words[4])));
        })
                .filter((FilterFunction<StationLog>) value -> value.getDuration() > 0)

                // 当Flink已Event Timer模式处理数据流时，它会根据数据里的时间戳来处理基于时间的算子
                // 给一个3秒的延迟
                // 乱序数据设置时间戳和watermark
                // AssignerWithPeriodicWatermarks 周期
                .assignTimestampsAndWatermarks(WatermarkStrategy.<StationLog>forBoundedOutOfOrderness(Duration.ofSeconds(3))
                        .withTimestampAssigner((SerializableTimestampAssigner<StationLog>) (element, recordTimestamp) -> {
                            return element.getCallTime(); //指定EventTime对应的字段
                        }))
                // 分组操作,按照基站进行分组操作
                .keyBy(StationLog::getStationID)
                // 基于时间驱动，每隔5s计算一下最近10s的数据
                .timeWindow(Time.seconds(10), Time.seconds(5))
                .reduce(new MyReduceFunction(), new MyProcessWindows())
                .print();

    }

    //用于如何处理窗口中的数据，即：找到窗口内通话时间最长的记录。
    static class MyReduceFunction implements ReduceFunction<StationLog> {
        private static final long serialVersionUID = 1748355616364915629L;

        @Override
        public StationLog reduce(StationLog value1, StationLog value2) throws Exception {
            // 找到通话时间最长的通话记录
            return value1.getDuration() >= value2.getDuration() ? value1 : value2;
        }
    }
    //窗口处理完成后，输出的结果是什么
    static class MyProcessWindows extends ProcessWindowFunction<StationLog, String, String, TimeWindow> {
        private static final long serialVersionUID = 4428121047315559165L;

        @Override
        public void process(String key, ProcessWindowFunction<StationLog, String, String, TimeWindow>.Context context,
                            Iterable<StationLog> elements, Collector<String> out) throws Exception {
            StationLog maxLog = elements.iterator().next();

            StringBuffer sb = new StringBuffer();
            sb.append("窗口范围是:").append(context.window().getStart()).append("----").append(context.window().getEnd()).append("\n");;
            sb.append("基站ID：").append(maxLog.getStationID()).append("\t")
                    .append("呼叫时间：").append(maxLog.getCallTime()).append("\t")
                    .append("主叫号码：").append(maxLog.getFrom()).append("\t")
                    .append("被叫号码：")	.append(maxLog.getTo()).append("\t")
                    .append("通话时长：").append(maxLog.getDuration()).append("\n");
            out.collect(sb.toString());
        }
    }
}
