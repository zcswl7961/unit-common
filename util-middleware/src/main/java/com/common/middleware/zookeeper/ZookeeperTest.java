package com.common.middleware.zookeeper;

import java.util.List;

/**
 * @author: zhoucg
 * @date: 2019-10-21
 */
public class ZookeeperTest {

    public static void main(String[] args) throws Exception {
        BaseZookeeper baseZookeeper = new BaseZookeeper();
        baseZookeeper.connectZookeeper("192.168.129.128:2181");

        List<String> children = baseZookeeper.getChildren("/");
        for(String path: children) {
            System.out.println(path);
        }
        baseZookeeper.closeConnection();
    }
}
