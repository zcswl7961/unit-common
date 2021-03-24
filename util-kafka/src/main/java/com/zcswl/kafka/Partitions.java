package com.zcswl.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.Node;

import java.util.List;
import java.util.Map;

/**
 * Test
 * kafka生产者消息设置自定义分区
 * @author zhoucg
 * @date 2019-10-22
 */
public class Partitions implements Partitioner{

    /**
     *
     * @param topic 指定topic数据
     * @param key 当前key
     * @param keyBytes 当前key的字节数组
     * @param value 当前value
     * @param valueBytes 当前value的字节数组
     * @param cluster 当前集群信息
     * @return 返回的值对应当前消息的partition
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        /**
         * 可以根据对应的集群环境和对应的value数据进行负载指定message对应的partition数据
         */
        List<Node> nodes = cluster.nodes();
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
