package com.common.util.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

/**
 * Created by zhoucg on 2019-02-27.
 */
public class KafkaTest {

    private static final Logger log = LoggerFactory.getLogger(KafkaTest.class);

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.1.156.18:9192");
        props.put("group.id", "CountryCounter");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //1.创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        //2.订阅Topic
        //创建一个只包含单个元素的列表，Topic的名字叫作customerCountries
        consumer.subscribe(Collections.singletonList("RT"));
        //支持正则表达式，订阅所有与test相关的Topic
        //consumer.subscribe("test.*");

        //3.轮询
        //消息轮询是消费者的核心API，通过一个简单的轮询向服务器请求数据，一旦消费者订阅了Topic，轮询就会处理所欲的细节，包括群组协调、partition再均衡、发送心跳
        //以及获取数据，开发者只要处理从partition返回的数据即可。
        try {
            while (true) {//消费者是一个长期运行的程序，通过持续轮询向Kafka请求数据。在其他线程中调用consumer.wakeup()可以退出循环
                //在100ms内等待Kafka的broker返回数据.超市参数指定poll在多久之后可以返回，不管有没有可用的数据都要返回
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    log.debug(record.topic() + record.partition() + record.offset() + record.key() + record.value());
                }
            }
        } finally {
            //退出应用程序前使用close方法关闭消费者，网络连接和socket也会随之关闭，并立即触发一次再均衡
            consumer.close();
        }
    }
}
