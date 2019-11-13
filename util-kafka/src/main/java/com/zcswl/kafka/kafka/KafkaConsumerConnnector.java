package com.zcswl.kafka.kafka;

import com.google.common.collect.Lists;
import com.zcswl.kafka.config.KafkaProperties;
import com.zcswl.kafka.handler.MqMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * 消费者
 * @author zhoucg
 * @date 2019-11-13 10:42
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class KafkaConsumerConnnector {

    private final KafkaProperties prop;

    /**
     * kafka消费者消息数据
     * @param topic 指定的topic数
     * @param handler 处理的逻辑
     * @param threadCount 执行线程处理
     */
    public void consumer(String topic, final MqMessageHandler handler, Integer threadCount) {
        if (threadCount < 1) {
            throw new IllegalArgumentException("出现消息的线程数最少为1");
        }
        //设置处理消息线程数，线程数应小于等于partition数量，若线程数大于partition数量，则多余的线程则闲置，不会进行工作
        //key:topic名称 value:线程数
        //获取指定kafka topic -> partitions
        ConsumerBuilder consumerBuilder = new ConsumerRunnable(prop.getServers(),prop.getGroupId(),topic);
        Map<String, List<PartitionInfo>> topics = consumerBuilder.get().listTopics();
        if (!topics.containsKey(topic)) {
            return;
        }
        List<PartitionInfo> partitions = topics.get(topic);
        if(partitions.size() < threadCount) {
            threadCount = partitions.size();
        }
        ConsumerGroup consumerGroup = new ConsumerGroup(threadCount,prop.getGroupId(),topic,prop.getServers(),handler);
        consumerGroup.execute();
    }

    private static class ConsumerGroup{

        private List<ConsumerRunnable> consumers;

        /**
         * 创建消费者组
         */
        public ConsumerGroup(int consumerNum,String groupId,String topic,String brokerList,MqMessageHandler handler) {
            consumers = Lists.newArrayList();
            for(int i = 0;i<consumerNum;i++) {
                ConsumerRunnable consumerRunnable = new ConsumerRunnable(brokerList,groupId,topic,handler);
                consumers.add(consumerRunnable);
            }
        }

        /**
         * 消费
         */
        public void execute() {
            for(ConsumerRunnable runnable : consumers) {
                new Thread(runnable).start();
            }
        }
    }

    /**
     * KafkaConsumer Builder
     */
    public static abstract class ConsumerBuilder {

        public KafkaConsumer<String,String> kafkaConsumer = null;

        void builer(String brokerList,String groupId,String topic) {
            //消费者配置文件
            Properties properties = new Properties();
            //kafka的列表数据
            properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerList);
            //消费组编号
            properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, groupId);
            properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
            //反序列化key
            properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            //反序列化value
            properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            //自定提交
            properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);
            //自定提交每一秒进行执行
            properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG,"1000");
            kafkaConsumer= new KafkaConsumer<>(properties);
            kafkaConsumer.subscribe(Arrays.asList(topic));
        }

        public KafkaConsumer get() {
            return kafkaConsumer;
        }
    }

    /**
     * 消费者消费线程
     */
    private static class ConsumerRunnable extends ConsumerBuilder implements Runnable{

        private MqMessageHandler handler = null;

        /**
         * 每一个线程拥有一个消息KafkaConsumer对象
         * @param brokerList kafka集群配置
         * @param groupId 消费者groupid
         * @param topic 指定topic
         * @param handler 任务执行
         */
        public ConsumerRunnable(String brokerList,String groupId,String topic,MqMessageHandler handler) {
            this(brokerList,groupId,topic);
            this.handler = handler;
        }

        /**
         * 每一个线程拥有一个消息KafkaConsumer对象
         * @param brokerList kafka集群配置
         * @param groupId 消费者groupid
         * @param topic 指定topic
         */
        public ConsumerRunnable(String brokerList,String groupId,String topic){
            builer(brokerList, groupId, topic);
        }

        @Override
        public void run() {
            while (true) {
                ConsumerRecords<String, String> records = get().poll(200);
                records.forEach(record -> {
                    log.info( "当前线程名称 : " + Thread.currentThread().getName() + ", 主题名称 :" + record.topic() + ", 分区名称 :" + record.partition() + ", 位移名称 :" + record.offset() + ", value :" + record.value());
                    handler.handle(record.value());
                });
            }
        }
    }

}
