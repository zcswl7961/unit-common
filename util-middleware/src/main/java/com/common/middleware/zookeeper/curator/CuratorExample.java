package com.common.middleware.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.UnhandledErrorListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

/**
 * Apache CuratorLeaderElectionDemo
 * http://curator.apache.org/getting-started.html
 * @author zhoucg
 * @date 2021-03-19 14:31
 */
public class CuratorExample {

    public static void main(String[] args) throws Exception {
        String zookeeperConnectionString = "192.168.129.128:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);

        client.start();

        // 创建节点
        client.create()
                .withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/NEW","newData".getBytes())
        ;

        // 获取节点信息，追加Watcher
        Stat stat = new Stat();
        byte[] bytes = client.getData()
                .storingStatIn(stat)
                .usingWatcher(new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                        System.out.println(event);
                    }
                })
                .forPath("/NEW");
        // getData
        byte[] bytes1 = client.getData()
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                        System.out.println("event:" + event);
                    }
                })
                .withUnhandledErrorListener(new UnhandledErrorListener() {
                    @Override
                    public void unhandledError(String message, Throwable e) {
                        System.out.println("unhandledError");
                    }
                })
                .forPath("/NEW");

        // delete
        /*client.delete()
                .deletingChildrenIfNeeded()
                .withVersion(-1)
                .forPath("/WL");*/

        client.delete()
                .withVersion(-1)
                .inBackground((client1, event) -> {
                    // CuratorFramework client, CuratorEvent event
                    System.out.println("event:"+event);
                })
                .withUnhandledErrorListener((message, e) -> {
                    // String message, Throwable e
                    System.out.println("unhandledError");
                })
                .forPath("/NEW");


        Thread.sleep(50000);
    }
}
