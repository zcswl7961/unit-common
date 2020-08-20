package com.common.jdk.cas;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * @author zhoucg
 * @date 2020-08-17 10:59
 */
public class ReentrantReadWriteLockTest {

    private volatile ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * cache message
     */
    private final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    public void read() {
        try {
            lock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + " read start, current time"+ System.currentTimeMillis());
            String result = map.getOrDefault("zhoucg", "wlllllllllll");

            System.out.println(Thread.currentThread().getName() + " read end, current result:["+ result +"] current time:" + System.currentTimeMillis());
        }  finally {
            lock.readLock().unlock();
        }
    }


    public void writer() {
        try {
            lock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + " writer start");
            map.put("zhoucg","wl");
            Thread.sleep(10000);
            System.out.println(Thread.currentThread().getName() + " writer end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        // 读读线程
        ReentrantReadWriteLockTest test = new ReentrantReadWriteLockTest();

        Thread t1 = new Thread(test::read);
        Thread t2 = new Thread(test::read);

        t1.start();
        t2.start();

        // 写线程
        Thread r1 = new Thread(test::writer);
        //r1.join();
        r1.start();

        // 读读不互斥 读写互斥  写读互斥  写写互斥
        // 一个线程必须等待另一个线程执行完成，这个就是互斥
    }


}
