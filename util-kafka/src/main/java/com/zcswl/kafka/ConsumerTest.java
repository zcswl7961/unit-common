package com.zcswl.kafka;

import com.zcswl.kafka.config.KafkaProperties;
import com.zcswl.kafka.handler.Handler;
import com.zcswl.kafka.kafka.KafkaConsumerConnnector;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * kafka消费者消费消息
 *
 * 消费者的概念：
 *      消费者组是Kafka实现单播和广播两种消息模型的手段。同一个topic，每个消费者组都可以拿到相同的全部数据。
 * @author zhoucg
 * @date 2021-03-23 21:39
 */
public class ConsumerTest {

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
        kafkaConsumerConnnector.consumer("test-3", new Handler() {
            @Override
            public void handle(String message) {

            }
        }, 3, "WL");

    }
}
