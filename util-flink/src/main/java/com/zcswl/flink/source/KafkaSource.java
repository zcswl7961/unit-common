package com.zcswl.flink.source;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

import java.util.Properties;

/**
 * @author zhoucg
 * @date 2021-02-27 15:41
 */
public class KafkaSource {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 配置对应的kafka consumer
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "192.168.129.128");
        props.setProperty("group.id", "topic-18");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        env.enableCheckpointing(5000);

        DataStream<String> stream = env.addSource(new FlinkKafkaConsumer011<String>("demo", new SimpleStringSchema(), props));
        stream.print();

        env.execute("fink-connector-kafka");
        // flink-connector-kafka
    }
}
