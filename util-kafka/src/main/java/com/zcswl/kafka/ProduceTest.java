package com.zcswl.kafka;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.zcswl.kafka.config.KafkaProperties;
import com.zcswl.kafka.kafka.KafkaProducerConnector;
import lombok.Data;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.configurationprocessor.json.JSONObject;

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
        kafkaPropertiesProducer.setLingerMs(0L);
        kafkaPropertiesProducer.setAck("-1");
        kafkaPropertiesProducer.setRetries(3);
        // partitions -> 3
        kafkaPropertiesProducer.setSendTopic("zhoucg-wl"); // 原来的版本topic-18
        KafkaProducerConnector kafkaProducerConnector = new KafkaProducerConnector(kafkaPropertiesProducer);
        kafkaProducerConnector.init();


        AtomicInteger fin = new AtomicInteger();

        Random random = new Random();
        new Thread(() -> {
            while (true) {
                // 每隔0.5秒发送一次消息
                try {
                    //String message = fin +"=="+"ZHOUCG:WL:"+random.nextLong();
                    /*Future<RecordMetadata> send = kafkaProducerConnector.send(message);
                    RecordMetadata recordMetadata = send.get();
                    System.out.println("向kafka发送消息："+message+" 当前对应的partition："+recordMetadata.partition()+ " 当前对应的offset："+recordMetadata.offset());*/

                    Message message = new Message();
                    message.setName("zhoucg"+random.nextLong());
                    message.setAddress("浙江省杭州市西湖区"+random.nextInt(1000)+"号");
                    message.setAge(random.nextInt());
                    String s = JSONUtil.toJsonStr(message);
                    kafkaProducerConnector.send(s).get();
                    Thread.sleep(2000);
                    fin.getAndIncrement();
                } catch (Exception e) {
                    System.out.println("报错误："+e);
                }
            }
        }).start();
    }


    @Data
    private static class Message {

        private String name;
        private String address;
        private int age;
    }
}
