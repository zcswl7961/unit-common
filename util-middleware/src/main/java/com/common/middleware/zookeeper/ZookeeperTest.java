package com.common.middleware.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
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

        List<String> children = baseZookeeper.getChildren("/kafka-group");
        for(String path: children) {
            System.out.println(path);
        }
        /**
         * 创建一个节点
         */
        //String s = baseZookeeper.creatNode("/zhoucg", "192.168.129.128");
        //System.out.println(s);


        //String s1 = baseZookeeper.creatNode("/zhoucg/ips", "192.168.129.128:2181;192.168.12.21:2181");
        //System.out.println(s1);

        Stat stat = new Stat();
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

        Thread.sleep(100000);

        baseZookeeper.closeConnection();


    }
}
