package com.common.util.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhoucg on 2019-03-28.
 * ReentrantLock源码解读
 */
public class ReentrantLockDemo {

    private static ReentrantLock lock = new ReentrantLock();

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
