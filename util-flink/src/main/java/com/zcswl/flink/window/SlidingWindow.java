package com.zcswl.flink.window;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * 滑动窗口
 * 滑动窗口和翻滚窗口类似，区别在于：滑动窗口可以有重叠的部分。
 * 在滑窗中，一个元素可以对应多个窗口。
 * @author zhoucg
 * @date 2021-03-02 16:38
 */
public class SlidingWindow {


    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 测试
        DataStream<String> dataStreamSource = env.socketTextStream("192.168.129.128", 8888);

        SingleOutputStreamOperator<Tuple2<String, Integer>> map = dataStreamSource.map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String value) throws Exception {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                long timeMillis = System.currentTimeMillis();

                int random = new Random().nextInt(10);
                System.out.println("sliding value : " + value + " random : " + random + " timestamp : " + timeMillis + "|" + format.format(timeMillis));
                return new Tuple2<>(value, random);
            }
        });


        // 基于事件驱动，每隔2个事件，触发一次计算，本次窗口的大小为3，代表窗口里的每种事件最多为3个
        // 每隔2个计算最近3个的结果
        WindowedStream<Tuple2<String, Integer>, Tuple, GlobalWindow> countWindow = map.keyBy(0)
                .countWindow(3, 2);

        // 基于时间驱动，每隔5s计算一下最近10s的数据
        WindowedStream<Tuple2<String, Integer>, Tuple, TimeWindow> timeWindow = map.keyBy(0)
                .timeWindow(Time.seconds(10), Time.seconds(5));

        // sum 算子
        countWindow.sum(1).print();

        env.execute();
    }
}
