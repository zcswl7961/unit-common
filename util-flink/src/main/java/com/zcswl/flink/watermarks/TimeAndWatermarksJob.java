package com.zcswl.flink.watermarks;

import org.apache.flink.api.common.eventtime.*;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.io.Serializable;
import java.time.Duration;
import java.util.Random;

/**
 * https://www.pianshen.com/article/23541455835/
 * https://blog.csdn.net/zhaoyuqiang/article/details/107453466 1.11 watermark代码demo
 *
 * 时间语义和Watermark（水位线）
 * 时间语义:
 *      Event Time:是事件创建的时间。它通常由事件中的  事件创建时间可以从日志数据中提取
 *                  时间戳描述，例如日志数据中的每一条记录都会带有时间戳，Flink通过时间戳分配器访问事件时间戳。
 *      Ingestion Time: 事件进入Flink的时间
 *      Processing Time：事件被处理时机器的系统时间
 * @author zhoucg
 * @date 2021-03-03 9:50
 */
public class TimeAndWatermarksJob {

    public static void main(String[] args) {

        // env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置时间语义
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        //设置周期性的产生水位线的时间间隔。当数据流很大的时候，如果每个事件都产生水位线，会影响性能。
        env.getConfig().setAutoWatermarkInterval(100);//默认100毫秒

        // source
        DataStream<String> dataStreamSource = env.socketTextStream("127.0.0.1", 8888);

        DataStream<Sensor> dataStreamMap = dataStreamSource.map((MapFunction<String, Sensor>) value -> {
            Random random = new Random();
            int sensor = random.nextInt(10000);
            return new Sensor(value, sensor, System.currentTimeMillis());
        });

        // 当Flink以Event Timer模式处理数据流时，它会根据数据里的时间戳来处理基于时间的算子
        // 给一个100毫秒的延迟
        // 乱序数据设置时间戳和watermark
        // AssignerWithPeriodicWatermarks 周期
        dataStreamMap.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<Sensor>(Time.milliseconds(100)) {
            private static final long serialVersionUID = -5516988401920275163L;

            @Override
            public long extractTimestamp(Sensor element) {
                return element.getTimestamp();
            }
        });

        // 升序数据设置事件时间和watermark
        // AssignerWithPunctuatedWatermarks
        dataStreamMap.assignTimestampsAndWatermarks(new AscendingTimestampExtractor<Sensor>() {
            @Override
            public long extractAscendingTimestamp(Sensor element) {
                return element.getTimestamp();
            }
        });


        // 1.11 版本的写法
        dataStreamMap.assignTimestampsAndWatermarks(WatermarkStrategy.<Sensor>forBoundedOutOfOrderness(Duration.ofSeconds(3))
                        .withTimestampAssigner((SerializableTimestampAssigner<Sensor>) (element, recordTimestamp) -> {
                            return element.getTimestamp(); //指定EventTime对应的字段
                        }));

    }




    /**
     * flink stream element
     */
    public static class Sensor implements Serializable {

        private static final long serialVersionUID = 5383058200128525264L;
        private String name;

        private Integer sensor;

        /**
         * 基于Event Time:事件创建的时间 进行的操作，事件元素中必须要含有对应的时间
         */
        private Long timestamp;

        public Sensor() {}

        public Sensor(String name, int sensor, Long timestamp) {
            this.name = name;
            this.sensor = sensor;
            this.timestamp = timestamp;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getSensor() {
            return sensor;
        }

        public void setSensor(Integer sensor) {
            this.sensor = sensor;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }
    }
}
