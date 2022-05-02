package com.zcswl.flink.window;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * https://www.cnblogs.com/ronnieyuan/p/11847568.html
 *
 * 滚动窗口
 * 翻滚窗口能将数据流切分成不重叠的窗口，每一个事件只能属于一个窗口
 * 翻滚窗具有固定的尺寸，不重叠。
 * @author zhoucg
 * @date 2021-03-02 16:03
 */
public class TumblingWindow {


    public static void main(String[] args) throws Exception {

        // env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> dataStream = env.socketTextStream("192.168.129.128", 8888);
        
        // map
        SingleOutputStreamOperator<Tuple2<String, Integer>> map = dataStream.map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String value) throws Exception {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                long timeMillis = System.currentTimeMillis();

                int random = new Random().nextInt(10);
                System.out.println("value: " + value + " random: " + random + "timestamp: " + timeMillis + "|" + format.format(timeMillis));
                return new Tuple2<>(value, random);
            }
        });

        // 基于时间统计，每10秒统计一次
        // <T> The type of elements in the stream. 流的元属类型
        // <K> The type of the key by which elements are grouped. 通过哪种元属类型进行分组
        // <Window> 窗口信息
        WindowedStream<Tuple2<String, Integer>, Tuple, TimeWindow> timeWindow = map.keyBy(0)
                .timeWindow(Time.seconds(10));

        // 基于事件驱动，总计100个进行统计
        WindowedStream<Tuple2<String, Integer>, Tuple, GlobalWindow> countWindow = map.keyBy(0)
                .countWindow(100);

        // 全窗口函数
        timeWindow.apply(new MyTimeWindowFunction()).setParallelism(1).print();

        // 增量聚合函数
        SingleOutputStreamOperator<String> aggregate = timeWindow.aggregate(new MyAggregateFunction());


        env.execute();
    }

    /**
     * WindowFunction<IN, OUT, KEY, W extends Window>
     *
     *     IN : 每一个参数的输入操作
     *     OUT : 参数的输出
     *     KEY：对应的key值结构
     *     Window:窗口
     *
     *     void apply(KEY key, W window, Iterable<IN> input, Collector<OUT> out)
     */
    private static class MyTimeWindowFunction implements WindowFunction<Tuple2<String, Integer>, String, Tuple, TimeWindow> {

        private static final long serialVersionUID = -4502032584909864764L;

        @Override
        public void apply(Tuple tuple, TimeWindow window, Iterable<Tuple2<String, Integer>> input, Collector<String> out) throws Exception {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            int sum = 0;

            for(Tuple2<String,Integer> tuple2 : input){
                sum +=tuple2.f1;
            }

            long start = window.getStart();
            long end = window.getEnd();

            out.collect("key:" + tuple.getField(0) + " value: " + sum + "| window_start :"
                    + format.format(start) + "  window_end :" + format.format(end)
            );
        }
    }


    /**
     * <IN>  表示流的输入类型
     * ACC:  累加器，表示中间的聚合状态（accumulator）
     * OUT:  输出器
     */
    private static class MyAggregateFunction implements AggregateFunction<Tuple2<String,Integer>, InnerObject, String> {

        @Override
        public InnerObject createAccumulator() {
            return new InnerObject();
        }

        @Override
        public InnerObject add(Tuple2<String, Integer> value, InnerObject accumulator) {
            accumulator.setX(accumulator.getX() + value.f1);
            return accumulator;
        }

        @Override
        public String getResult(InnerObject accumulator) {
            return String.valueOf(accumulator.getX());
        }

        /**
         * session window
         */
        @Override
        public InnerObject merge(InnerObject a, InnerObject b) {
            InnerObject innerObject = new InnerObject();
            innerObject.setX(a.getX() + b.getX());
            return innerObject;
        }
    }



    /**
     * 测试AggregateFunction
     */
    private static class InnerObject {
        private String name;
        private int x;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }
    }
}
