package com.zcswl.kafka.kafka;

import com.zcswl.kafka.config.KafkaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author zhoucg
 * @date 2019-11-13 10:22
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class KafkaProducerConnector {

    private final KafkaProperties properties;

    private static KafkaProducer<String,byte[]> producer = null;

    @PostConstruct
    public void init() {

        Map<String,Object> config = new HashMap<>(16);

        //kafka服务器地址
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,properties.getServers());
        //kafka消息序列化类 即将传入对象序列化为字节数组
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,properties.getValueSerializer());
        //kafka消息key序列化类 若传入key的值，则根据该key的值进行hash散列计算出在哪个partition上
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,properties.getKeySerializer());
        //当多条消息发送到同一个partition时，该值控制生产者批量发送消息的大小，批量发送可以减少生产者到服务端的请求数，有助于提高客户端和服务端的性能。
        config.put(ProducerConfig.BATCH_SIZE_CONFIG,properties.getBatchSize());
        //往kafka服务器提交消息间隔时间，0则立即提交不等待
        config.put(ProducerConfig.LINGER_MS_CONFIG,properties.getLingerMs());
        //kakfa的ack模式 0，1，-1（all）
        config.put(ProducerConfig.ACKS_CONFIG,properties.getAck());
        //生产者发送失败后，重试的次数
        config.put(ProducerConfig.RETRIES_CONFIG,properties.getRetries());
        //此处对应的是kafka定义分区的类
        if(properties.getPartitioner() != null) {
            config.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,properties.getPartitioner());
        }
        producer = new KafkaProducer<>(config);
    }


    /**
     * 发送消息数据
     * @param value
     * @return
     * @throws Exception
     */
    public Future<RecordMetadata> send(Serializable value) throws Exception{
        try {
            //将对象序列化称字节码
            byte[] bytes= SerializationUtils.serialize(value);
            Future<RecordMetadata> future=producer.send(new ProducerRecord(properties.getSendTopic(),bytes));
            return future;
        }catch(Exception e){
            log.error("current send message error：{}",e);
            throw e;
        }
    }


}
