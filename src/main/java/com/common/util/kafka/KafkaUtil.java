package com.common.util.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by zhoucg on 2018-09-25.
 */
public class KafkaUtil {

    private static KafkaProducer<String,byte[]> producer = null;

    private static ConsumerConnector consumer = null;

    static {
        Map<String,Object> config = new HashMap<>();

        //kafka服务器地址
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"10.1.152.212:9192");
        //kafka消息序列化类 即将传入对象序列化为字节数组
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.ByteArraySerializer");
        //kafka消息key序列化类 若传入key的值，则根据该key的值进行hash散列计算出在哪个partition上
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        config.put(ProducerConfig.BATCH_SIZE_CONFIG,1024*1024*5);
        //往kafka服务器提交消息间隔时间，0则立即提交不等待
        config.put(ProducerConfig.LINGER_MS_CONFIG,0);
        //消费者配置文件
        Properties properties = new Properties();

        properties.put("zookeeper.connect","10.1.152.212:2181");

        properties.put("group.id","123");

        properties.put("auto.commit.interval.ms","1000");

        ConsumerConfig consumerConfig = new ConsumerConfig(properties);

        producer = new KafkaProducer<>(config);

        consumer = Consumer.createJavaConsumerConnector(consumerConfig);


    }


    /**
     *启动一个消费程序
     * @param topic 要消费的topic名称
     * @param handler 自己的处理逻辑的实现
     * @param threadCount 消费线程数，该值应小于等于partition个数，多了也没用
     */
    public static <T extends Serializable>void startConsumer(String topic, final MqMessageHandler<T> handler, int threadCount) throws Exception{
        if(threadCount<1)
            throw new Exception("处理消息线程数最少为1");
        //设置处理消息线程数，线程数应小于等于partition数量，若线程数大于partition数量，则多余的线程则闲置，不会进行工作
        //key:topic名称 value:线程数
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(threadCount));

        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap =  consumer.createMessageStreams(topicCountMap);


        //Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        //声明一个线程池，用于消费各个partition
        ExecutorService executor= Executors.newFixedThreadPool(threadCount);
        //获取对应topic的消息队列
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
        //为每一个partition分配一个线程去消费
        for (final KafkaStream stream : streams) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    ConsumerIterator<byte[], byte[]> it = stream.iterator();
                    //有信息则消费，无信息将会阻塞
                    while (it.hasNext()){
                        T message=null;
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
                }
            });
        }
    }

    /**
     *发送消息，发送的对象必须是可序列化的
     */
    public static Future<RecordMetadata> send(String topic, Serializable value) throws Exception{
        try {
            //将对象序列化称字节码
            byte[] bytes=SerializationUtils.serialize(value);
            Future<RecordMetadata> future=producer.send(new ProducerRecord<String,byte[]>(topic,bytes));
            return future;
        }catch(Exception e){
            throw e;
        }
    }

    //内部抽象类 用于实现自己的处理逻辑
    public static abstract class MqMessageHandler<T extends Serializable>{
        public abstract void handle(T message);
    }


    public static void main(String[] args) throws Exception {
        //发送一个信息
        send("test",new User("id","userName", "password"));
        //为test启动一个消费者，启动后每次有消息则打印对象信息
        KafkaUtil.startConsumer("test", new MqMessageHandler<User>() {
            @Override
            public void handle(User user) {
                //实现自己的处理逻辑，这里只打印出消息
                System.out.println(user.toString());
            }
        },2);
    }

    static class User implements Serializable{

        private static final long serialVersionUID = 8576358642877640767L;

        private People people;

        private String name;

        private int age;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public People getPeople() {
            return people;
        }

        public void setPeople(People people) {
            this.people = people;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id='" + id + '\'' +
                    ", userName='" + userName + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }

        private String id;
        private String userName;
        private String password;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public User(){}

        public User(String id,String userName,String password){
            this.id = id;
            this.userName = userName;
            this.password = password;
        }
    }

    static class People {
        private String name;

        private String age ;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

}
