package com.common.util.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author: zhoucg
 * @date: 2019-08-13
 */
public class SimpleKafkaProducer {

    private static KafkaProducer<String, String> producer;
    private final static String TOPIC = "hadoop";
    public SimpleKafkaProducer(){
        Properties config = simpleProducerConfig("10.1.241.38:9192,10.1.241.39:9192,10.1.241.42:9192");
        producer = new KafkaProducer<String, String>(config);
    }
    public void produce(){
        /*if(true) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, String.valueOf(System.currentTimeMillis()), "测试数据");
            if("async".equalsIgnoreCase("asynac")){
                try{
                    producer.send(producerRecord, new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata metadata, Exception exception) {
                            if(exception != null) {
                            }
                        }
                    });
                }catch (Throwable e) {
                    //TODO这里基本上不会爆出kafka的异常，因为是一个异步发送
                    throw new RuntimeException(e);
                }
            }else{
                try {
                    RecordMetadata recordMetadata = producer.send(producerRecord).get();
                    System.out.println(recordMetadata);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        }*/

        for (int i = 30;i<40;i++){
            String key = String.valueOf(i);
            String data = "hello kafka message："+key;
            producer.send(new ProducerRecord<String, String>(TOPIC,String.valueOf(System.currentTimeMillis()),data));
            System.out.println(data);
        }
        producer.close();
    }

    public static void main(String[] args) {
        new SimpleKafkaProducer().produce();
    }


    public static Properties simpleProducerConfig(String bootstrapServers) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
