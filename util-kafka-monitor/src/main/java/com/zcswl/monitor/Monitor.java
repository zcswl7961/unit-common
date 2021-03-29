package com.zcswl.monitor;

import kafka.admin.AdminClient;
import kafka.coordinator.GroupOverview;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.protocol.types.SchemaException;
import scala.collection.JavaConversions;
import scala.util.parsing.json.JSONObject;

import java.util.*;

/**
 * kafka 的 topic message 存在于zookeeper 和Boker中
 * 从kafka-0.9版本及以后，kafka的消费者组和offset信息就不存zookeeper了，而是存到broker服务器上，所以，如果你为某个消费者指定了一个消费者组名称（group.id），那么，一旦这个消费者启动，这个消费者组名和它要消费的那个topic的offset信息就会被记录在broker服务器上。
 * @author zhoucg
 * @date 2021-03-26 9:09
 */
public class Monitor {


    public static void main(String[] args) {
        // 获取对应的AdminClint
        String brokerList = "192.168.129.128:9192";
        // 监控对应的consumerGroup的信息(这个实际上，并不能进行判断，因为每一个table -> topic， 而每一个topic，对应支撑核心而言，是都有一个消费组的概念的)
        //String consumerGroup = "";



        AdminClient adminClient = getAdminClient(brokerList);
        // 获取当前cluster 对应的所有的消费组信息，broker 中
        Set<String> consumerGroupSet = collectionConsumerGroup(adminClient);
        // topic -> 消费组的关系
        Map<String, Set<String>> topicNameConsumerGroupMap = new HashMap<>();
        // 消费组 -> ConsumerGroupSummary
        Map<String, AdminClient.ConsumerGroupSummary> stringConsumerGroupSummaryMap = collectConsumerGroupSummary(adminClient, consumerGroupSet, topicNameConsumerGroupMap);
        // 获取对应的消息堆积量信息,消息堆积量就是当前partition 的offset -> 对应的consumer消费的offset 信息

        // 最新接收时间 -> 对应的partition上的offset的Record的时间
        // 最新处理时间 -> 对应的是consumer消费的offset 对应的Record的时间
        // 当前消费节点 -> 所有partition对应的offset 数据的总和


    }


    /**
     * 获取指定broker对应的AdminClient 数据
     */
    private static AdminClient getAdminClient(String brokerList) {
        return AdminClient.create(getProperties(brokerList, false));
    }

    /**
     * 获取指定broker对应的KafkaConsumer 数据
     */
    public static KafkaConsumer createConsumerClient(String brokerList, Integer maxPollRecords) {
        Properties properties = getProperties(brokerList, false);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        properties.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15000);
        return new KafkaConsumer(properties);
    }


    /**
     * properties
     * serialize ： 是否进行序列换操作 对应AdminClient,KafkaConsumer 创建，false ，对于KafkaProducer 创建 true
     */
    private static Properties getProperties(String brokerList, Boolean serialize) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        // 序列化操作
        if (serialize) {
            properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        } else {
            properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
            properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        }
        return properties;
    }

    /**
     * 获取当前集群对应的消费组信息
     */
    private static Set<String> collectionConsumerGroup(AdminClient adminClient) {
        Set<String> consumerGroupSet = new HashSet<String>();
        scala.collection.immutable.Map<org.apache.kafka.common.Node, scala.collection.immutable.List<kafka.coordinator.GroupOverview>> brokerGroupMap = adminClient.listAllGroups();
        for (scala.collection.immutable.List<kafka.coordinator.GroupOverview> brokerGroup : JavaConversions.asJavaMap(brokerGroupMap).values()) {
            List<GroupOverview> lists = JavaConversions.asJavaList(brokerGroup);
            for (kafka.coordinator.GroupOverview groupOverview : lists) {
                String consumerGroup = groupOverview.groupId();
                if (consumerGroup != null && consumerGroup.contains("#")) {
                    String[] splitArray = consumerGroup.split("#");
                    consumerGroup = splitArray[splitArray.length - 1];
                }
                consumerGroupSet.add(consumerGroup);
            }
        }
        return consumerGroupSet;
    }

    /**
     * 计算 消费组summary信息
     *  consumerGroupSet: 集群消费组信息
     *  topicNameConsumerGroupMap ： key 对应的是 topicName value 对应的是消费组的信息
     *  Map<String, AdminClient.ConsumerGroupSummary> ：消费组 -> ConsumerGroupSummary
     */
    private static Map<String, AdminClient.ConsumerGroupSummary> collectConsumerGroupSummary(AdminClient adminClient,
                                                                                      Set<String> consumerGroupSet,
                                                                                      Map<String, Set<String>> topicNameConsumerGroupMap) {
        if (consumerGroupSet == null || consumerGroupSet.isEmpty()) {
            return new HashMap<>();
        }

        Map<String, AdminClient.ConsumerGroupSummary> consumerGroupSummaryMap = new HashMap<>();
        for (String consumerGroup : consumerGroupSet) {
            try {
                AdminClient.ConsumerGroupSummary consumerGroupSummary = adminClient.describeConsumerGroup(consumerGroup);
                if (consumerGroupSummary == null) {
                    continue;
                }
                consumerGroupSummaryMap.put(consumerGroup, consumerGroupSummary);

                java.util.Iterator<scala.collection.immutable.List<AdminClient.ConsumerSummary>> it =
                        JavaConversions.asJavaIterator(consumerGroupSummary.consumers().iterator());
                while (it.hasNext()) {
                    List<AdminClient.ConsumerSummary> consumerSummaryList = JavaConversions.asJavaList(it.next());
                    for (AdminClient.ConsumerSummary consumerSummary: consumerSummaryList) {
                        List<TopicPartition> topicPartitionList = JavaConversions.asJavaList(consumerSummary.assignment());
                        if (topicPartitionList == null) {
                            continue;
                        }
                        for (TopicPartition topicPartition: topicPartitionList) {
                            Set<String> groupSet = topicNameConsumerGroupMap.getOrDefault(topicPartition.topic(), new HashSet<>());
                            groupSet.add(consumerGroup);
                            topicNameConsumerGroupMap.put(topicPartition.topic(), groupSet);
                        }
                    }
                }
            } catch (SchemaException e) {
                //LOGGER.error("schemaException exception, clusterId:{} consumerGroup:{}.", clusterId, consumerGroup, e);
            } catch (Exception e) {
                //LOGGER.error("collect consumerGroupSummary failed, clusterId:{} consumerGroup:{}.", clusterId, consumerGroup, e);
            }
        }
        return consumerGroupSummaryMap;
    }


    private Map<String, Set<String>> collectTopicAndConsumerGroupMap(AdminClient adminClient,
                                                                     Set<String> consumerGroupSet,
                                                                     Map<String, Set<String>> topicNameConsumerGroupMap) {
        if (consumerGroupSet .isEmpty()) {
            return new HashMap<>(0);
        }
        for (String consumerGroup: consumerGroupSet) {
            try {
                Map<TopicPartition, Object> topicPartitionAndOffsetMap = JavaConversions.asJavaMap(adminClient.listGroupOffsets(consumerGroup));
                for (Map.Entry<TopicPartition, Object> entry : topicPartitionAndOffsetMap.entrySet()) {
                    TopicPartition tp = entry.getKey();
                    Set<String> subConsumerGroupSet = topicNameConsumerGroupMap.getOrDefault(tp.topic(), new HashSet<>());
                    subConsumerGroupSet.add(consumerGroup);
                    topicNameConsumerGroupMap.put(tp.topic(), subConsumerGroupSet);
                }
            } catch (Exception e) {
                // LOGGER.error("update consumer group failed, clusterId:{} consumerGroup:{}.", clusterId, consumerGroup, e);
            }
        }
        return topicNameConsumerGroupMap;
    }

    /**
     *  获取指定topic 对应的consumerGroup的消费组信息
     */
    private static Map<Integer, String> getConsumeIdMap(ConsumerMetadata consumerMetadata, Long clusterId, String topicName, String consumerGroup) {
        AdminClient.ConsumerGroupSummary consumerGroupSummary = consumerMetadata.getConsumerGroupSummaryMap().get(consumerGroup);
        if (consumerGroupSummary == null) {
            return new HashMap<>(0);
        }
        Map<Integer, String> consumerIdMap = new HashMap<>();
        for (scala.collection.immutable.List<AdminClient.ConsumerSummary> scalaSubConsumerSummaryList: JavaConversions.asJavaList(consumerGroupSummary.consumers().toList())) {
            List<AdminClient.ConsumerSummary> subConsumerSummaryList = JavaConversions.asJavaList(scalaSubConsumerSummaryList);
            for (AdminClient.ConsumerSummary consumerSummary: subConsumerSummaryList) {
                for (TopicPartition tp: JavaConversions.asJavaList(consumerSummary.assignment())) {
                    if (!tp.topic().equals(topicName)) {
                        continue;
                    }
                    consumerIdMap.put(tp.partition(), consumerSummary.host().substring(1, consumerSummary.host().length()) + ":" + consumerSummary.consumerId());
                }
            }
        }
        return consumerIdMap;
    }



    /**
     * 根据group，topic获取broker中group中各个消费者的offset
     */
    private static Map<Integer, String> getOffsetByGroupAndTopicFromBroker(AdminClient adminClient, String consumerGroup, String topicName)  {
        Map<Integer, String> result = new HashMap<>();
        if (null == adminClient) {
            return result;
        }
        Map<TopicPartition, Object> offsetMap = JavaConversions.asJavaMap(adminClient.listGroupOffsets(consumerGroup));
        for (Map.Entry<TopicPartition, Object> entry : offsetMap.entrySet()) {
            TopicPartition topicPartition = entry.getKey();
            if (topicPartition.topic().equals(topicName)) {
                result.put(topicPartition.partition(), entry.getValue().toString());
            }
        }
        return result;
    }

    /**
     * 创建消费者
     */
    private static Properties createKafkaConsumerProperties(String brokerList) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        properties.setProperty(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);
        properties.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 15000);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return properties;
    }


    /**
     * 收集器
     */
    private static class ConsumerMetadata {
        private Set<String> consumerGroupSet = new HashSet<>();

        private Map<String, Set<String>> topicNameConsumerGroupMap = new HashMap<>();

        private Map<String, AdminClient.ConsumerGroupSummary> consumerGroupSummaryMap = new HashMap<>();

        public ConsumerMetadata(Set<String> consumerGroupSet,
                                Map<String, Set<String>> topicNameConsumerGroupMap,
                                Map<String, AdminClient.ConsumerGroupSummary> consumerGroupSummaryMap) {
            this.consumerGroupSet = consumerGroupSet;
            this.topicNameConsumerGroupMap = topicNameConsumerGroupMap;
            this.consumerGroupSummaryMap = consumerGroupSummaryMap;
        }

        public Set<String> getConsumerGroupSet() {
            return consumerGroupSet;
        }

        public Map<String, Set<String>> getTopicNameConsumerGroupMap() {
            return topicNameConsumerGroupMap;
        }

        public Map<String, AdminClient.ConsumerGroupSummary> getConsumerGroupSummaryMap() {
            return consumerGroupSummaryMap;
        }
    }



}
