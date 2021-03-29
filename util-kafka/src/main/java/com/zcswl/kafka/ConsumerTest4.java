package com.zcswl.kafka;

import com.zcswl.kafka.config.KafkaProperties;
import com.zcswl.kafka.handler.Handler;
import com.zcswl.kafka.kafka.KafkaConsumerConnnector;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * @author zhoucg
 * @date 2021-03-26 14:05
 */
public class ConsumerTest4 {

    public static void main(String[] args) {

        KafkaProperties kafkaProperties = new KafkaProperties();
        kafkaProperties.setAck("1");
        kafkaProperties.setAutoCommitMs("200");
        kafkaProperties.setBatchSize(200);
        kafkaProperties.setServers("192.168.129.128:9192");
        kafkaProperties.setGroupId("groupId");
        kafkaProperties.setValueSerializer(StringDeserializer.class.getName());
        KafkaConsumerConnnector kafkaConsumerConnnector = new KafkaConsumerConnnector(kafkaProperties);

        // 测试对应的partitions -> 3 的数据，
        kafkaConsumerConnnector.consumer("test-4", new Handler() {
            @Override
            public void handle(String message) {

            }
        }, 4, "WL");

    }
}
