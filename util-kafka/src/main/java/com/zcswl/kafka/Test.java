package com.zcswl.kafka;

import com.google.common.collect.Lists;
import com.zcswl.kafka.config.KafkaProperties;
import com.zcswl.kafka.kafka.KafkaConsumerConnnector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhoucg
 * @date 2021-03-23 13:37
 */
public class Test {

    public static void main(String[] args) throws Exception {

        monitor();
    }


    /**
     * 客户端，监控操作
     */
    private static void monitor() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        KafkaProperties kafkaProperties = new KafkaProperties();
        kafkaProperties.setServers("192.168.129.128:9192");
        kafkaProperties.setGroupId("groupId");
        kafkaProperties.setValueSerializer(StringDeserializer.class.getName());


        KafkaConsumerConnnector.ConsumerBuilder consumerBuilder = new KafkaConsumerConnnector.ConsumerRunnable(kafkaProperties.getServers(),kafkaProperties.getGroupId(),"test-zhoucg");
        KafkaConsumer<String,String> kafkaConsumer =  consumerBuilder.kafkaConsumer;
        List<PartitionInfo> partitionInfos = kafkaConsumer.partitionsFor("test-3");
        partitionInfos.forEach(System.out::println);

        Collection<TopicPartition> topicPartitions = getTopicPartitions(partitionInfos);
        // 开始对应的offset 对于一个partition的结果
        //
        kafkaConsumer.beginningOffsets(topicPartitions).forEach((o, o2) -> System.out.println(o + " beginning " + o2));
        // 结束的offset
        kafkaConsumer.endOffsets(topicPartitions).forEach((o, o2) -> System.out.println(o + " ending " + o2));

        // 获取一个topic
        KafkaConsumerConnnector.ConsumerBuilder connector = new KafkaConsumerConnnector.ConsumerRunnable(kafkaProperties.getServers(),kafkaProperties.getGroupId());
        KafkaConsumer<String,String> consumer =  connector.kafkaConsumer;


        // begin
        consumer.assign(topicPartitions);
        consumer.seekToBeginning(topicPartitions);
        ConsumerRecords<String, String> beginConsumerRecords = consumer.poll(1000);
        System.out.println("begin isEmpty:" + beginConsumerRecords.isEmpty() + ",size:" + beginConsumerRecords.count());
        for (ConsumerRecord consumerRecord : beginConsumerRecords) {
            System.out.println("begin timestamp：" + consumerRecord.topic() + "_" + consumerRecord.partition() + "," + dateFormat.format(new Date(consumerRecord.timestamp())));
        }


        //last
        consumer.seekToEnd(topicPartitions);
        ConsumerRecords<String, String> lastConsumerRecords = consumer.poll(1000);
        System.out.println("last isEmpty:" + lastConsumerRecords.isEmpty() + ",size:" + lastConsumerRecords.count());
        for (ConsumerRecord consumerRecord : lastConsumerRecords) {
            System.out.println("last timestamp：" + consumerRecord.topic() + "_" + consumerRecord.partition() + "," + dateFormat.format(new Date(consumerRecord.timestamp())));
        }
        kafkaConsumer.close();
    }


    private static Collection<TopicPartition> getTopicPartitions(List<PartitionInfo> partitionInfos){
        ArrayList<TopicPartition> topicPartitions = Lists.newArrayList();
        for (PartitionInfo partitionInfo : partitionInfos) {
            TopicPartition topicPartition = new TopicPartition(partitionInfo.topic(), partitionInfo.partition());
            topicPartitions.add(topicPartition);
        }
        return topicPartitions;
    }
}
