package com.common.middleware.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;

/**
 * 选主
 * @author zhoucg
 * @date 2021-03-19 15:20
 */
public class CuratorLeaderElectionDemo {

    public static void main(String[] args) throws InterruptedException {
        String zookeeperConnectionString = "192.168.129.128:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);

        client.start();

        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i =0 ;i < 5 ;i ++) {
            new Thread(() -> {
                LeaderSelectorListener listener = new LeaderSelectorListenerAdapter() {
                    public void takeLeadership(CuratorFramework client) throws Exception {
                        System.out.println("当前线程："+Thread.currentThread().getName()+"作为了Leader");
                        // this callback will get called when you are the leader
                        // do whatever leader work you need to and only exit
                        // this method when you want to relinquish leadership
                        Thread.sleep(2000L);
                        System.out.println("当前线程："+Thread.currentThread().getName()+"退出了Leader");
                    }
                };

                LeaderSelector selector = new LeaderSelector(client, "/LEAD", listener);
                try {
                    countDownLatch.countDown();
                    countDownLatch.await();
                    selector.autoRequeue();  // not required, but this is behavior that you will probably expect
                    selector.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(200000L);
    }
}
