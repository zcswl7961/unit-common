package com.zcswl.flink.window;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * 滑动窗口/滚动窗口 元属类型类
 * @author zhoucg
 * @date 2021-03-02 21:26
 */
public class SlidingAndTumblingWindowElement {

    public static void main(String[] args) throws Exception {
        // env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> dataStream = env.socketTextStream("192.168.129.128", 8888);

        // 转换成对应的格式
        DataStream<Element> dataStreamMap = dataStream.map((MapFunction<String, Element>) value -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            long timeMillis = System.currentTimeMillis();

            int random = new Random().nextInt(10);
            System.out.println("SlidingAndTumbling value : " + value + " random : " + random + " timestamp : " + timeMillis + "|" + format.format(timeMillis));
            return new Element(value, random);
        });
        /**
         * WindowStream：以指定对应的keyBy 分组的结果 ，泛型
         * WindowedStream<T, K, W extends Window>
         *     T：表示元素类型
         *     K：分组类型
         *     Window: 窗口类型
         *
         */


        // 基于时间的窗口滚动
        // 每隔十秒统计一次
        WindowedStream<Element, String, TimeWindow> elementStringTimeWindowWindowedStream = dataStreamMap.keyBy(Element::getValue)
                .timeWindow(Time.seconds(10));

        // 基于事件的窗口滚动
        // 每隔100个进行统计
        WindowedStream<Element, String, GlobalWindow> elementStringGlobalWindowWindowedStream = dataStreamMap.keyBy(Element::getValue)
                .countWindow(100);

        // 基于时间的窗口滑动，每隔5s计算一下最近10s的数据
        WindowedStream<Element, String, TimeWindow> elementStringTimeWindowWindowedStream1 = dataStreamMap.keyBy(Element::getValue)
                .timeWindow(Time.seconds(10), Time.seconds(5));

        // 基于事件的窗口滑动
        // 每隔2个计算最近3个的结果
        WindowedStream<Element, String, GlobalWindow> elementStringGlobalWindowWindowedStream1 = dataStreamMap.keyBy(Element::getValue)
                .countWindow(3, 2);
        /**
         * 增量聚合
         * windowStream.aggreate(MyAggregateFunction)
         * MyAggregateFunction :
         *
         * 全量聚合：
         * windowStream.apply(WindowFunction)
         * windowStream.process(ProcessWindowFunction)
         * WindowFunction
         * ProcessWindowFunction
         */

        /**
         * windowStream
         * reduce()
         * aggreate()
         * apply()
         *
         */


        /**
         * windowStream.trigger() 触发器
         *
         */
        env.execute();
    }

    /**
     * flink stream 元素类型
     */
    private static class Element {
        private String value;
        private int count;

        public Element() {}

        public Element(String value, int count) {
            this.value = value;
            this.count = count;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
