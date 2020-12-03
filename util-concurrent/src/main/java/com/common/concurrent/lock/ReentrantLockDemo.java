package com.common.concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhoucg on 2019-03-28.
 * ReentrantLock源码解读
 * 可重入锁机制：可重入就是说某个线程已经获得某个锁，可以再次获取锁而不会出现死锁。
 *
 */
public class ReentrantLockDemo {

    // 内部是是通过Sync进行控制，分为公平锁和非公平锁   NonfairSync FairSync
    private static ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) {
        for (int i =0 ;i < 10;i++) {
            Thread thread = new Thread(() -> {

                lock.lock();
                try{
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }


            });
            thread.start();
        }
    }
}
