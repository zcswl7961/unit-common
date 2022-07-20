package com.zcswl.flink.connector;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/**
 * @author xingyi
 * @date 2022/7/19
 */
public class KafkaConnectorDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // kafka
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "172.16.100.109:9092");
        properties.setProperty("group.id", "test");


        FlinkKafkaConsumer<String> myConsumer = new FlinkKafkaConsumer<>(
                java.util.regex.Pattern.compile("xingyi_test_topic"),
                new SimpleStringSchema(),
                properties);

        env.setParallelism(1);

        DataStreamSource<String> stringDataStreamSource = env.addSource(myConsumer);

        stringDataStreamSource.print();

        env.execute();
    }
}
