package com.common.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zhoucg on 2019-02-22.
 * kafka服务端
 * 将消息发送到kafka的指定topic下
 */
public class KafkaProducerOps{

    public static void main(String[] args) throws Exception{


        Properties properties = new Properties();

        InputStream inputStream = KafkaProducerOps.class.getClassLoader().getResourceAsStream("producer.properties");
        properties.load(inputStream);

        Producer<String,String> producer = new KafkaProducer<>(properties);
        String topic = "hadoop";
        String key = "1";
        String value = "今天是个好天气";

        /**
         * kafka同步发送
         */
        ProducerRecord<String,String> producerRecord = new ProducerRecord<>(topic,key,value);
        producer.send(producerRecord);
        producer.close();

        /**
         * kafka异步发送
         */
        producer.send(producerRecord,(metadata,exception) -> {
            System.out.println("kafka异步调用报错");
        });


    }



}
