package com.common.middleware.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @author: zhoucg
 * @date: 2019-10-21
 */
public class ZookeeperTest {

    public static void main(String[] args) throws Exception {
        BaseZookeeper baseZookeeper = new BaseZookeeper();
        baseZookeeper.connectZookeeper("192.168.129.128:2181");

        /*List<String> children = baseZookeeper.getChildren("/kafka-group");
        for(String path: children) {
            System.out.println(path);
        }*/
        /**
         * 创建一个节点
         */
        //String s = baseZookeeper.creatNode("/zhoucg", "192.168.129.128");
        //System.out.println(s);


        //String s1 = baseZookeeper.creatNode("/zhoucg/ips", "192.168.129.128:2181;192.168.12.21:2181");
        //System.out.println(s1);



        // getData 设置Watcher
        /*Stat stat = new Stat();
        byte[] data = baseZookeeper.getZookeeper().getData("/zhoucg/ips", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                Event.KeeperState state = event.getState();
                Event.EventType type = event.getType();
                String path = event.getPath();
                System.out.println("path="+path+"; state"+state+";type"+type);

            }
        }, stat);
        String s = new String(data);
        System.out.println(s);
        Thread.sleep(100000);*/


        // 创建一个临时节点
        // 受限制与initTime 和tickTime的设置，如果在initTime * tickTime时间之内，当前客户端任然没有连接到服务，zookeeper
        // 集群服务认为此客户端已经断开连接，会清除掉设置的临时顺序节点
        /*ZooKeeper zookeeper = baseZookeeper.getZookeeper();
        String s = zookeeper.create("/LOCK/", "zhoucg".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(s);*/



        //Thread.sleep(50000);
        //baseZookeeper.closeConnection();


    }
}
