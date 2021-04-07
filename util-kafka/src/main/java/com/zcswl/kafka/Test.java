package com.zcswl.kafka;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import kafka.admin.AdminClient;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * @author zhoucg
 * @date 2021-03-23 13:37
 *
 * test-3-2 beginning 2711
 * test-3-0 beginning 2710
 * test-3-1 beginning 2715
 * test-3-2 ending 2730
 * test-3-0 ending 2729
 * test-3-1 ending 2734
 */
public class Test {

    public static void main(String[] args) throws Exception {

        //monitor();
        /*KafkaConsumer<String,String> kafkaConsumer =  build("192.168.129.128:9192", "groupId1122");
        TopicPartition topicPartition = new TopicPartition("topic-2", 0);
        kafkaConsumer.assign(Collections.singletonList(topicPartition));
        kafkaConsumer.seek(topicPartition,0);
        ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
        ArrayList<ConsumerRecord<String, String>> consumerRecords = Lists.newArrayList(records);
        ConsumerRecord<String, String> consumerRecord = consumerRecords.get(0);
        System.out.println(consumerRecord.value());*/
        Properties props = initConfig();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        /*consumer.subscribe(Collections.singletonList("topic-10000"));

        Set<TopicPartition> assignment = new HashSet<>();
        // 在poll()方法内部执行分区分配逻辑，该循环确保分区已被分配。
        // 当分区消息为0时进入此循环，如果不为0，则说明已经成功分配到了分区。
        while (assignment.size() == 0) {
            consumer.poll(100);
            // assignment()方法是用来获取消费者所分配到的分区消息的
            // assignment的值为：topic-demo-3, topic-demo-0, topic-demo-2, topic-demo-1
            assignment = consumer.assignment();
        }
        System.out.println(assignment);

        for (TopicPartition tp : assignment) {
            int offset = 0;
            System.out.println("分区 " + tp + " 从 " + offset + " 开始消费");
            consumer.seek(tp, offset);
        }

        // 消费一次数据
        ConsumerRecords<String, String> records = consumer.poll(1000);
        // 消费记录
        ArrayList<ConsumerRecord<String, String>> consumerRecords = Lists.newArrayList(records);
        ConsumerRecord<String, String> consumerRecord = consumerRecords.get(0);
        System.out.println(consumerRecord.value());*/

        consumer.assign(Collections.singletonList(new TopicPartition("topic-18", 0)));
        //ConsumerRecords<String, String> poll = consumer.poll(Duration.ofMillis(200));

        //boolean empty = poll.isEmpty();
        //System.out.println(empty);


        /*// consumer.subscribe(Collections.singletonList("topic-2"));
        Set<TopicPartition> assignment = new HashSet<>();
        while (assignment.size() == 0) {
            // seek之前poll一次，由系统内部分配一次分区
            consumer.poll(100);
            assignment = consumer.assignment();
        }
        // 找到最新的
        TopicPartition seekCurrentTopicPartition = null;
        long seekCurrentTopicOffset = Long.MIN_VALUE;
        for (TopicPartition topicPartition : assignment) {
            long offset = Long.parseLong(offsetMap.get(topicPartition.partition()));
            if (offset > seekCurrentTopicOffset) {
                seekCurrentTopicOffset = offset;
                seekCurrentTopicPartition = topicPartition;
            }
        }
        consumer.seek(seekCurrentTopicPartition, seekCurrentTopicOffset);
        ConsumerRecords<String, String> poll1 = consumer.poll(1000);
        List<ConsumerRecord<String, String>> lastPush = Lists.newArrayList(poll1);
        ConsumerRecord<String, String> consumerRecord = lastPush.get(0);
        System.out.println(consumerRecord.value());*/




    }


    protected static Properties initConfig() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.129.128:9192");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "121s12");
        return properties;


        // 当前线程名称 : WL-0, 主题名称 :test-3, 分区名称 :1, 位移名称 :6518, value :8030==ZHOUCG:WL:1319401547402250401


        /**
         * 客户端，监控操作
         *//*
    private static void monitor() throws InterruptedException, ExecutionException, TimeoutException {
        //List<String> groupsForTopic = getGroupsForTopic("192.168.129.128:9192", "test-3");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        KafkaConsumer<String,String> kafkaConsumer =  build("192.168.129.128:9192", "groupId111");
        List<PartitionInfo> partitionInfos = kafkaConsumer.partitionsFor("test-3");
        partitionInfos.forEach(System.out::println);

        Collection<TopicPartition> topicPartitions = getTopicPartitions(partitionInfos);
        // 开始对应的offset 对于一个partition的结果
        //
        Map<TopicPartition, Long> topicStartPartitionLongMap = kafkaConsumer.beginningOffsets(topicPartitions);
        // startTopicPartitionLongMap.forEach((o, o2) -> System.out.println(o + " begin " + o2));

        // 结束的offset
        Map<TopicPartition, Long> topicEndPartitionLongMap = kafkaConsumer.endOffsets(topicPartitions);
        // kafkaConsumer.endOffsets(topicPartitions).forEach((o, o2) -> System.out.println(o + " ending " + o2));


        // 计算对应数据的堆积量信息
        long messageStacking = 0;
        long miniOffset = Long.MAX_VALUE;
        long maxOffset = Long.MIN_VALUE;
        TopicPartition topicPartition = null;
        Set<TopicPartition> topicPartitions1 = topicStartPartitionLongMap.keySet();
        for(TopicPartition partition : topicStartPartitionLongMap.keySet()) {
            Long partitionOffset = topicStartPartitionLongMap.get(partition);
            Long partitionCurrentOffset = topicEndPartitionLongMap.get(partition);
            if (partitionOffset < miniOffset) {
                miniOffset = partitionOffset;
            }
            if (partitionCurrentOffset > maxOffset) {
                maxOffset = partitionCurrentOffset;
                topicPartition= partition;
            }
            messageStacking += partitionCurrentOffset - partitionOffset;
        }
        // 获取对应的消费延时
        kafkaConsumer.assign(topicPartitions);
        *//*kafkaConsumer.assign(topicPartitions);
        kafkaConsumer.seekToBeginning(topicPartitions);
        ConsumerRecords<String, String> poll = kafkaConsumer.poll(1000);
        // we only get Last Message for detail Time
        List<ConsumerRecord> consumerRecords = Lists.newArrayList(poll);
        ConsumerRecord last = consumerRecords.get(consumerRecords.size()-1);
        // 最后消费时间
        long timestamp = last.timestamp();
        System.out.println(last.value());*//*
        // 最新接收时间，由kafka producer 推送到指定partition的最后offset的时间
        kafkaConsumer.seek(topicPartition, maxOffset-1);
        ConsumerRecords<String, String> poll1 = kafkaConsumer.poll(1000);
        List<ConsumerRecord> lastPush = Lists.newArrayList(poll1);
        ConsumerRecord consumerRecord1 = lastPush.get(0);
        long timestamp1 = consumerRecord1.timestamp();

//        System.out.println("当前系统消息对接："+ messageStacking + ":" + "最新接收时间："+ dateFormat.format(new Date(timestamp1)) + "" +
//                "最新处理时间：" + dateFormat.format(new Date(timestamp)) +  "时间差：" + (timestamp1 - timestamp) + "最新offset"+ maxOffset);
        kafkaConsumer.close();

    }

    private static KafkaConsumer<String, String> build(String brokerList, String groupId) {
        //消费者配置文件
        Properties properties = new Properties();
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerList);
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //自定提交
        // 禁止自动提交位移
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
        return new KafkaConsumer<>(properties);
    }


    private static Collection<TopicPartition> getTopicPartitions(List<PartitionInfo> partitionInfos){
        ArrayList<TopicPartition> topicPartitions = Lists.newArrayList();
        for (PartitionInfo partitionInfo : partitionInfos) {
            TopicPartition topicPartition = new TopicPartition(partitionInfo.topic(), partitionInfo.partition());
            topicPartitions.add(topicPartition);
        }
        return topicPartitions;
    }*/
    }

    private static KafkaConsumer<String, String> build(String brokerList, String groupId) {
        //消费者配置文件
        Properties properties = new Properties();
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerList);
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        //自定提交
        // 禁止自动提交位移
        properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
        return new KafkaConsumer<>(properties);
    }

}
