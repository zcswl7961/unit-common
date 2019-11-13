package com.zcswl.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 接收spring.kafka的配置信息
 * @author zhoucg
 * @date 2019-11-13 10:08
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {

    /**
     * 服务地址
     */
    private String servers;

    /**
     * kafka消息序列化类 即将传入对象序列化为字节数组
     */
    private String valueSerializer;

    /**
     * kafka消息key序列化类 若传入key的值，则根据该key的值进行hash散列计算出在哪个partition上
     */
    private String keySerializer;

    /**
     * 当多条消息发送到同一个partition时，该值控制生产者批量发送消息的大小，批量发送可以减少生产者到服务端的请求数，
     * 有助于提高客户端和服务端的性能。
     */
    private Long batchSize;

    /**
     * 往kafka服务器提交消息间隔时间，0则立即提交不等待
     */
    private Long lingerMs;

    /**
     * kakfa的ack模式 0，1，-1（all）
     */
    private String ack;

    /**
     * 生产者发送失败后，重试的次数
     */
    private Integer retries;

    /**
     * 此处对应的是kafka定义分区的类
     */
    private String partitioner;

    /**
     * 发送到kafka的指定topic数据
     */
    private String sendTopic;

    /**
     * 消费者消费的groupId数据
     */
    private String groupId;

    /**
     * 消费者自动提交的毫秒数
     */
    private Long autoCommitMs;

}
