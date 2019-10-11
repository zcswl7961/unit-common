package com.common.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.InputStream;
import java.util.*;

/**
 * Created by zhoucg on 2019-02-22.
 * kafka消费端，
 */
public class KafkaConsumerOps {


    public static void main(String[] args) throws Exception{

        Properties properties = new Properties();
        InputStream in = KafkaConsumerOps.class.getClassLoader().getResourceAsStream("consumer.properties");
        properties.load(in);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        /**
         * 消费者订阅topic
         */
        Collection<String> topics = Arrays.asList("hadoop");
        consumer.subscribe(topics);
        while (true) {
            // 接下来就要从topic中拉取数据
            ConsumerRecords<String, String> records = consumer.poll(1000);
            if(!records.isEmpty()) {
                for(ConsumerRecord<String, String> record : records) {
                    String msg = record.value();
                    String recordTopic = record.topic();
                    long offset = record.offset();
                    int partition = record.partition();
                    String key = record.key();
                    System.out.format("%d\t%d\t%s\t%s\n", offset, partition, key, msg);
                }
                consumer.commitSync();
            }
        }
    }
}
