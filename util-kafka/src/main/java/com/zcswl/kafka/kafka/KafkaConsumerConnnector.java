package com.zcswl.kafka.kafka;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.zcswl.kafka.config.KafkaProperties;
import com.zcswl.kafka.handler.MqMessageHandler;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * 消费者
 * @author zhoucg
 * @date 2019-11-13 10:42
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class KafkaConsumerConnnector<T extends Serializable> {

    private final KafkaProperties properties;

    private static ConsumerConnector consumer = null;
    /**
     * 线程名称
     */
    private static final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("consumerKafka-pool-%d").build();

    @PostConstruct
    public void init() {

        //消费者配置文件
        Properties prop = new Properties();
        prop.put("zookeeper.connect",properties.getServers());
        prop.put("group.id",properties.getGroupId());
        prop.put("auto.commit.interval.ms",properties.getAutoCommitMs());
        ConsumerConfig consumerConfig = new ConsumerConfig(prop);
        consumer = Consumer.createJavaConsumerConnector(consumerConfig);
    }

    /**
     * kafka消费者消息数据
     * @param topic 指定的topic数
     * @param handler 处理的逻辑
     * @param threadCount 执行线程处理
     */
    public void consumer(String topic, final MqMessageHandler handler, Integer threadCount) {
        if(threadCount<1) {
            throw new IllegalArgumentException("出现消息的线程数最少为1");
        }
        //设置处理消息线程数，线程数应小于等于partition数量，若线程数大于partition数量，则多余的线程则闲置，不会进行工作
        //key:topic名称 value:线程数
        Map<String, Integer> topicCountMap = Maps.newHashMap();
        topicCountMap.put(topic, threadCount);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap =  consumer.createMessageStreams(topicCountMap);
        //声明一个线程池，用于消费各个partition
        ExecutorService executor = new ThreadPoolExecutor(threadCount, threadCount, 0L, MILLISECONDS,new LinkedBlockingDeque<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        //获取对应topic的消息队列
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
        //为每一个partition分配一个线程去消费
        for (final KafkaStream stream : streams) {
            executor.execute(() -> {
                ConsumerIterator<byte[], byte[]> it = stream.iterator();
                //有信息则消费，无信息将会阻塞
                while (it.hasNext()){
                    T message;
                    try {
                        //将字节码反序列化成相应的对象
                        byte[] bytes=it.next().message();
                        message = (T) SerializationUtils.deserialize(bytes);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    //调用自己的业务逻辑
                    try {
                        handler.handle(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }



}
