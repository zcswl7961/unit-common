package com.common.middleware.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Random;
import java.util.concurrent.CountDownLatch;


/**
 * 可重入锁
 * @author zhoucg
 * @date 2021-03-19 15:06
 */
public class CuratorDistributedLockDemo {

    public static void main(String[] args) throws Exception {
        String zookeeperConnectionString = "192.168.129.128:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);

        client.start();

        // 分布式锁
        CountDownLatch latch = new CountDownLatch(5);
        Random random = new Random();
        for (int i =0 ;i<5;i++) {
            new Thread(() -> {
                InterProcessMutex lock = new InterProcessMutex(client, "/LOCK");
                // InterProcessReadWriteLock 读写锁
                try {
                    latch.countDown();
                    latch.await();
                    // 模拟分布式获取锁操作
                    // 非等待时间过程，一直获取锁
                    lock.acquire();
                    // 模拟指定任务
                    int i1 = random.nextInt(3000);
                    System.out.println("当前线程："+Thread.currentThread().getName()+" 获取到锁， 执行任务："+i1+"毫秒");
                    Thread.sleep(i1);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.release();
                        System.out.println("当前线程："+Thread.currentThread().getName()+" 释放锁成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
