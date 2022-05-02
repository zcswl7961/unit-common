package com.zcswl.flink.watermarks;

import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.time.Duration;

/**
 * 每隔五秒，将过去是10秒内，通话时间最长的通话日志输出。
 *
 * @author zhoucg
 * @date 2021-03-03 22:43
 */
public class WaterMarkDemo {

    /**
     * watermark 算子之间的传递
     *
     */
    public static void main(String[] args) throws Exception {
        // 得到flink流式处理的运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置基于事件时间语义的流处理
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // 设置公共默认的并行度
        // env.setParallelism(1);
        // 设置周期的产生watermark的时间间隔，当数据流很大的时候，如果每个事件都产生水位线，影响性能
        env.getConfig().setAutoWatermarkInterval(100);

        // 通过socket获取流
        DataStream<String> dataStreamSource = env.socketTextStream("127.0.0.1", 8888);

        // 解决数据乱序的第三种策略，如果设置的数据延迟时间之后仍然延迟，就放到侧输出栏中
        OutputTag<StationLog> outputTag1 = new OutputTag<StationLog>("create"){};
        OutputTag<StationLog> outputTag = new OutputTag<>("create", TypeInformation.of(StationLog.class));


        /*
         *  结合代码中的程序设计，我们分析对应的事件时间语义下的关闭窗口：
         *      该代码中，设置了watermark的为每100毫秒进行产生一次，并且设置了桶乱序下的最大的乱序等待时间为：3 秒（WatermarkStrategy.<StationLog>forBoundedOutOfOrderness(Duration.ofSeconds(3)），
         *      滚动窗口事件10s（windowSize）同时设置了延迟等到的时间是5秒,并且设置了侧输出栏 outputTag
         *  假设我们流处理的第一个数据为（第五个为事件时间定义的时间戳）
         *      station1,18688822219,18684812319,10,1595158485855
         *      首先，系统会根据第一个事件时间戳 去生成第一个桶（TimeWindow）接收的事件事件错的范围：
         *      @see TumblingEventTimeWindows.of(size)     滚动窗口下：TumblingEventTimeWindows.assignWindows()
			            long start = TimeWindow.getWindowStartWithOffset(timestamp, offset, size);  return timestamp - (timestamp - offset + windowSize) % windowSize;
		 *                      5855 - (5855 - 0 + 10 ) % 10 = 5850
			            return Collections.singletonList(new TimeWindow(start, start + size));
			    即范围：【5850，5860） 【真正的事件时间戳】
	     *  因此，当当前系统产生的watermark达到5860的时候，进行一次桶计算，（注意这里不是关闭桶），
	     *      而系统中watermark的计算，是通过assignTimestampsAndWatermarks设置的 maxOutOfOrderness（最大乱序层度）进行算出来的
	     *      即：【当前事件时间戳 - maxOutOfOrderness = watermark值】
	     *  因此，触发当前【5850，5860）范围的第一次桶计算是当事件时间戳大于等于 5863 的时候触发的：
	     *      station1,18688822219,18684812319,10,1595158485863  -> 触发【5850，5860） 桶计算
	     *  而真正的 【5850，5860） 桶 关闭是再延迟乱序 5秒之后进行关闭
	     *      也就是说， 当当前事件时间戳为5868（watermark = 5865）的时候出发了桶关闭
	     *      station1,18688822219,18684812319,10,1595158485868  -> 触发【5850，5860） 桶关闭
	     *  而之后，假设再来了一个属于 【5850，5860）的数据，实际上都是进入了 outputTag 侧输出拦的
	     *
	     * ======理清=================================
         */

        // 流处理
        WindowedStream<StationLog, String, TimeWindow> windowWindowedStream = dataStreamSource
                // 流元素扁平化处理，转换成StationLog
                .flatMap(new FlatMapFunction<String, StationLog>() {
                    private static final long serialVersionUID = 9027908143422405274L;

                    @Override
                    public void flatMap(String value, Collector<StationLog> out){
                        String[] words = value.split(",");
                        out.collect(new StationLog(words[0], words[1], words[2], Long.parseLong(words[3]), Long.parseLong(words[4])));
 
                    }
                    // 设置对应的flatMap 算子的并行度为4
                })
                // 过滤
                .filter((FilterFunction<StationLog>) value -> value.getDuration() > 0)

                // 当Flink已Event Timer模式处理数据流时，它会根据数据里的时间戳来处理基于时间的算子，（就是计算对应的watermark）
                // 给一个3秒的延迟
                // watermark = eventTime - 延迟时间
                // <>watermark表示timestamp（时间时间）小于watermark的数据都已经到齐
                // 乱序数据设置时间戳和watermark
                // AssignerWithPeriodicWatermarks 周期

                .assignTimestampsAndWatermarks(WatermarkStrategy.<StationLog>forBoundedOutOfOrderness(Duration.ofSeconds(3))
                        .withTimestampAssigner((SerializableTimestampAssigner<StationLog>) (element, recordTimestamp) -> {
                            return element.getCallTime(); //指定EventTime对应的字段
                        }))

                // 表示的是时间有序，所以对应的时间就是按照事件时间
                // AscendingTimestampsWatermarks
                //.assignTimestampsAndWatermarks(WatermarkStrategy.<StationLog>forMonotonousTimestamps().withTimestampAssigner((SerializableTimestampAssigner<StationLog>) (element, recordTimestmp) -> {
                //    return element.getCallTime();
                //}))

                // 分组操作,按照基站进行分组操作
                .keyBy(StationLog::getStationID)
                // 基于时间驱动，每隔5s计算一下最近10s的数据
                //.timeWindow(Time.seconds(10), Time.seconds(5))
                .timeWindow(Time.seconds(10))
                // 解决事件乱序的另一个防范，设置数据延迟处理时间
                // 当对应的watermark触发了窗口计算的时候，会首先在窗口中计算一次数据
                // 设置延迟时间之后是来一个计算一次数据（桶不会关闭），但是同样针对watermark进行计算的
                .allowedLateness(Time.seconds(5))
                // 侧输出栏
                .sideOutputLateData(outputTag);


        SingleOutputStreamOperator<String> reduce = windowWindowedStream.reduce(new MyReduceFunction(), new MyProcessWindows());
        reduce.print();

        // 获取对应侧输出栏的结果
        DataStream<StationLog> sideOutput = reduce.getSideOutput(outputTag);
        // flink的侧输出栏如何判断是在哪个桶上延迟的呢？
        // 通过业务逻辑判断
        env.execute();
    }

    //用于如何处理窗口中的数据，即：找到窗口内通话时间最长的记录。
    static class MyReduceFunction implements ReduceFunction<StationLog> {
        private static final long serialVersionUID = 1748355616364915629L;
        @Override
        public StationLog reduce(StationLog value1, StationLog value2) {
            // 找到通话时间最长的通话记录
            return value1.getDuration() >= value2.getDuration() ? value1 : value2;
        }
    }
    //窗口处理完成后，输出的结果是什么
    static class MyProcessWindows extends ProcessWindowFunction<StationLog, String, String, TimeWindow> {
        private static final long serialVersionUID = 4428121047315559165L;

        @Override
        public void process(String key, ProcessWindowFunction<StationLog, String, String, TimeWindow>.Context context,
                            Iterable<StationLog> elements, Collector<String> out){
            StationLog maxLog = elements.iterator().next();
            String sb = "窗口范围是:" + context.window().getStart() + "----" + context.window().getEnd() + "\n" +
                    "基站ID：" + maxLog.getStationID() + "\t" +
                    "呼叫时间：" + maxLog.getCallTime() + "\t" +
                    "主叫号码：" + maxLog.getFrom() + "\t" +
                    "被叫号码：" + maxLog.getTo() + "\t" +
                    "通话时长：" + maxLog.getDuration() + "\n";
            out.collect(sb);
        }
    }
}
