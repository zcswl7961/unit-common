package com.zcswl.flink.processfunction;

import com.zcswl.flink.watermarks.StationLog;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.TimerService;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

/**
 * ProcessFunction
 * @author zhoucg
 * @date 2021-03-07 11:39
 */
public class ProcessFunctionDemo {

    public static void main(String[] args) throws Exception {

        // DataStream
        //      process(ProcessFunction processFunction)
        // KeyedStream
        //      process(KeyedProcessFunction keyedProcessFunction)
        // WindowStream
        //      process(WindowProcessFunction windowProcessFunction)
        // WindowAllStream
        //      WindowAllProcessFunction


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> dataStreamSource = env.socketTextStream("192.168.129.128", 8888);

        SingleOutputStreamOperator<StationLog> windowWindowedStream = dataStreamSource
                // 流元素扁平化处理，转换成StationLog
                .flatMap(new FlatMapFunction<String, StationLog>() {
                    private static final long serialVersionUID = 9027908143422405274L;

                    @Override
                    public void flatMap(String value, Collector<StationLog> out){
                        String[] words = value.split(",");
                        out.collect(new StationLog(words[0], words[1], words[2], Long.parseLong(words[3]), Long.parseLong(words[4])));

                    }
                    // 设置对应的flatMap 算子的并行度为4
                });

        windowWindowedStream.keyBy(StationLog::getStationID)
                .process(new MyKeyedProcessFunction())
                .print();


        env.execute();


    }

    /**
     * 定义KeyedProcessFunction 的实现
     */
    private static class MyKeyedProcessFunction extends KeyedProcessFunction<String, StationLog, String> {

        private static final long serialVersionUID = 9103738659892081135L;

        @Override
        public void processElement(StationLog value, Context ctx, Collector<String> out) throws Exception {
            // 获取当前的key
            ctx.getCurrentKey();
            // 设置侧输出流
            //ctx.output();
            // 获取当前处理时间
            Long timestamp = ctx.timestamp();

            TimerService timerService = ctx.timerService();
            // 获取当前时间语义Processing Time
            long l = timerService.currentProcessingTime();
            // 获取当前的WaterMark（事件时间语义下）
            long l1 = timerService.currentWatermark();
            // timerService.registerEventTimeTimer();
            // timerService.registerProcessingTimeTimer();

            // timerService.deleteProcessingTimeTimer();
            // timerService.deleteEventTimeTimer();
        }


        @Override
        public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {
            // processElement() timeService 设置完毕的操作
            super.onTimer(timestamp, ctx, out);
        }
    }
}
