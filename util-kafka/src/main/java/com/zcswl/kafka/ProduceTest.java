package com.zcswl.kafka;

import com.zcswl.kafka.config.KafkaProperties;
import com.zcswl.kafka.kafka.KafkaProducerConnector;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * kafka 生产端
 * @author zhoucg
 * @date 2021-03-23 21:33
 */
public class ProduceTest {

    public static void main(String[] args) throws Exception {

        KafkaProperties kafkaPropertiesProducer = new KafkaProperties();
        kafkaPropertiesProducer.setServers("192.168.129.128:9192");
        kafkaPropertiesProducer.setKeySerializer(StringSerializer.class.getName());
        kafkaPropertiesProducer.setValueSerializer(StringSerializer.class.getName());
        kafkaPropertiesProducer.setBatchSize(200);
        kafkaPropertiesProducer.setLingerMs(0L);
        kafkaPropertiesProducer.setAck("-1");
        kafkaPropertiesProducer.setRetries(3);
        // partitions -> 3
        kafkaPropertiesProducer.setSendTopic("test-3");
        KafkaProducerConnector kafkaProducerConnector = new KafkaProducerConnector(kafkaPropertiesProducer);
        kafkaProducerConnector.init();

        AtomicInteger fin = new AtomicInteger();
        Random random = new Random();
        new Thread(() -> {
            while (true) {
                // 每隔0.5秒发送一次消息
                try {
                    String message = fin +"=="+"ZHOUCG:WL:"+random.nextLong();
                    System.out.println("向kafka发送消息："+message);
                    kafkaProducerConnector.send(message);
                    Thread.sleep(500);
                    fin.getAndIncrement();
                } catch (Exception e) {
                    System.out.println("报错误："+e);
                }
            }
        }).start();
    }
}
