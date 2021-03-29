package com.zcswl.kafka.kafka;

import com.google.common.collect.Lists;
import com.zcswl.kafka.config.KafkaProperties;
import com.zcswl.kafka.handler.Handler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * 1，kafka消费端消费组的概念
 * 2，kafka消费端线程池对应partition分区
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
    public void consumer(String topic, final Handler handler, Integer threadCount, String threadNamePre) {
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
        // 随机性的线程消费对应的partition 分区
        ConsumerGroup consumerGroup = new ConsumerGroup(threadCount,prop.getGroupId(),topic,prop.getServers(),handler);

        // 指定线程消费指定的partition 分区
        ConsumerGroup consumerGroup1 = new ConsumerGroup(partitions, prop.getGroupId(), prop.getServers(),handler);

        // 通过指定线程策略，定义对应的线程
        consumerGroup.execute(threadNamePre);
    }

    private static class ConsumerGroup{

        private List<ConsumerRunnable> consumers;

        /**
         * 创建消费者组
         * 该方法使用kafkaConsumer.subscribe 方法，由系统内部进行线程绑定partition
         */
        public ConsumerGroup(int consumerNum,String groupId,String topic,String brokerList,Handler handler) {
            consumers = Lists.newArrayList();
            for(int i = 0;i<consumerNum;i++) {
                ConsumerRunnable consumerRunnable = new ConsumerRunnable(brokerList,groupId,topic,handler);
                consumers.add(consumerRunnable);
            }
        }

        /**
         * 创建消费线程组
         * 该方法通过指定线程-> partition的方式进行消费数据
         */
        public ConsumerGroup(List<PartitionInfo> partitionInfos, String groupId, String brokerList, Handler handler) {
            consumers = Lists.newArrayList();
            for (PartitionInfo partitionInfo : partitionInfos) {
                TopicPartition topicPartition = new TopicPartition(partitionInfo.topic(), partitionInfo.partition());
                ConsumerRunnable consumerRunnable = new ConsumerRunnable(brokerList, groupId, topicPartition, handler);
                consumers.add(consumerRunnable);
            }
        }

        /**
         * 消费
         */
        public void execute(String threadNamePre) {
            int threadId = 0;
            for(ConsumerRunnable runnable : consumers) {
                Thread thread = new Thread(runnable);
                thread.setName(threadNamePre+"-"+threadId);
                thread.start();
                threadId++;
            }
        }
    }

    /**
     * KafkaConsumer Builder
     */
    public static abstract class ConsumerBuilder {

        public KafkaConsumer<String,String> kafkaConsumer = null;

        void builer(String brokerList,String groupId,String topic) {
            // 消费者配置文件
            builer(brokerList, groupId);
            // 消费topic 消费全部分区
            kafkaConsumer.subscribe(Collections.singletonList(topic));
        }

        void builer(String brokerList,String groupId,TopicPartition topicPartition) {
            // 消费者配置文件
            builer(brokerList, groupId);
            // 订阅指定的分区信息
            kafkaConsumer.assign(Collections.singletonList(topicPartition));
        }

        void builer(String brokerList,String groupId) {
            //消费者配置文件
            Properties properties = new Properties();
            //kafka的broker_list 配置信息
            properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerList);
            //消费组编号
            properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, groupId);
            // 使用 earliest
            /**
             * 关于auto.offset.reset 得参数说明
             *      earliest
             *      latest
             *      none
             *      https://stackoverflow.com/questions/58829112/kafka-consumer-configuration-how-does-auto-offset-reset-controls-the-message-c
             */
            properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

            //反序列化key
            properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            //反序列化value
            properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            // 禁止自动提交位移
            // 自动提交再实际得生产环境中，一般会静止掉，
            // 已经消费了数据，但是offset没来得及提交（比如Kafka没有或者不知道该数据已经被消费）。
            // 使用手动提交offset得策略，防止因为kill 或者是异常错误导致offset没有来得及提交
            properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
            //properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);
            //自定提交每一秒进行执行
            //properties.put(org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG,"1000");
            kafkaConsumer= new KafkaConsumer<>(properties);
        }

        public KafkaConsumer get() {
            return kafkaConsumer;
        }
    }

    /**
     * 消费者消费线程
     */
    public static class ConsumerRunnable extends ConsumerBuilder implements Runnable{

        private Handler handler = null;

        /**
         * 指定当前线程assign 消费指定的partition
         */
        public ConsumerRunnable(String brokerList,String groupId,TopicPartition topicPartition,Handler handler) {
            builer(brokerList, groupId,topicPartition);
            this.handler = handler;
        }

        /**
         * 每一个线程拥有一个消息KafkaConsumer对象
         */
        public ConsumerRunnable(String brokerList,String groupId,String topic,Handler handler) {
            this(brokerList,groupId,topic);
            this.handler = handler;
        }

        /**
         * 每一个线程拥有一个消息KafkaConsumer对象
         */
        public ConsumerRunnable(String brokerList,String groupId,String topic){
            builer(brokerList, groupId, topic);
        }

        /**
         * 由外部决定是否assign 或者是 subscribe
         */
        public ConsumerRunnable(String brokerList,String groupId) {
            builer(brokerList, groupId);
        }

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                ConsumerRecords<String, String> records = get().poll(200);
                records.forEach(record -> {
                    //log.info( "当前线程名称 : " + Thread.currentThread().getName() + ", 主题名称 :" + record.topic() + ", 分区名称 :" + record.partition() + ", 位移名称 :" + record.offset() + ", value :" + record.value());
                    System.out.println( "当前线程名称 : " + Thread.currentThread().getName() + ", 主题名称 :" + record.topic() + ", 分区名称 :" + record.partition() + ", 位移名称 :" + record.offset() + ", value :" + record.value());
                    handler.handle(record.value());
                });
                /** *
                 * 权衡：延迟与数据一致性
                 *
                 * 如果您必须确保数据一致性，请选择commitSync()，因为它会确保在执行任何进一步操作之前，您将知道偏移提交是成功还是失败。但由于它是同步和阻塞，您将花费更多时间等待提交完成，这会导致高延迟。
                 * 如果您确定某些数据不一致并希望延迟较低，请选择commitAsync()，因为它不会等待完成。相反，它将稍后发出提交请求并处理来自Kafka的响应（成功或失败），同时，您的代码将继续执行。
                 */
                get().commitSync();
                Thread.sleep(5000);
            }
        }
    }

}
