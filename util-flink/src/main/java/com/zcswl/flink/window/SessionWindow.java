package com.zcswl.flink.window;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * 会话窗口
 * 会话窗口不重叠，没有固定的开始和结束时间
 * 与翻滚窗口和滑动窗口相反, 当会话窗口在一段时间内没有接收到元素时会关闭会话窗口。
 * 后续的元素将会被分配给新的会话窗口
 * @author zhoucg
 * @date 2021-03-02 16:54
 */
public class SessionWindow {

    public static void main(String[] args) throws Exception {
        // session window 窗口设置
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

        // 如果连续10s内，没有数据进来，则会话窗口断开。
        WindowedStream<Tuple2<String, Integer>, Tuple, TimeWindow> window = map.keyBy(0)
                .window(ProcessingTimeSessionWindows.withGap(Time.seconds(10)));

        // sum 算子
        window.sum(1).print();

        env.execute();
    }
}
