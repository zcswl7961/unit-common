package com.zcswl.flink;

import com.zcswl.flink.watermarks.StationLog;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;

/**
 * @author zhoucg
 * @date 2021-02-23 17:11
 */
public class LocalFlinkJob {

    private static int index = 1;


    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> dataStreamSource = env.socketTextStream("192.168.129.128", 8888);

        // 流处理
        KeyedStream<StationLog, String> windowWindowedStream = dataStreamSource
                // 流元素扁平化处理，转换成StationLog
                .flatMap(new FlatMapFunction<String, StationLog>() {
                    private static final long serialVersionUID = 9027908143422405274L;

                    @Override
                    public void flatMap(String value, Collector<StationLog> out){
                        String[] words = value.split(",");
                        out.collect(new StationLog(words[0], words[1], words[2], Long.parseLong(words[3]), Long.parseLong(words[4])));

                    }
                })
                // 过滤
                .filter((FilterFunction<StationLog>) value -> value.getDuration() > 0)
                .keyBy(StationLog::getStationID);


        windowWindowedStream.print().setParallelism(2);

        env.execute();


    }
}
