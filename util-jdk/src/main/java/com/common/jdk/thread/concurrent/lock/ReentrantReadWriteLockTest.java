package com.common.jdk.thread.concurrent.lock;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * 读写锁的使用示例场景：有大量的读线程，但少量的写（线程或次数） 的场景
 * @author zhoucg
 * @date 2020-08-17 10:59
 */
public class ReentrantReadWriteLockTest {

    private volatile ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * cache message
     */
    private static final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    public void read() {
        try {
            lock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + " read start, current time"+ System.currentTimeMillis());
            Thread.sleep(2000);
            String result = map.getOrDefault("zhoucg", "wlllllllllll");

            System.out.println(Thread.currentThread().getName() + " read end, current result:["+ result +"] current time:" + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }


    public void writer() {
        try {
            lock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + " writer start current time:" + System.currentTimeMillis());
            map.put("zhoucg","wl");
            Thread.sleep(8000);
            System.out.println(Thread.currentThread().getName() + " writer end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }


    public void writerOther() {
        try {
            lock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + " writer start current time:" + System.currentTimeMillis());
            // 如何保证写的有序性呢？
            if (map.getOrDefault("zhoucg","other").equals("wl")) {
                // 假设A的写在B之后执行，B修改了原始的写的值 wl
                map.put("zhoucg", "Awriter");
            } else {
                map.put("zhoucg","otherA");
            }
            Thread.sleep(8000);
            System.out.println(Thread.currentThread().getName() + " writer end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }

    }


    public static void main(String[] args) throws InterruptedException {

        long currentTime = System.currentTimeMillis();

        // 读读线程
        ReentrantReadWriteLockTest test = new ReentrantReadWriteLockTest();

        // 设置两个读线程
        Thread t1 = new Thread(test::read);
        Thread t2 = new Thread(test::read);
        t1.setName("读线程1");
        t2.setName("读线程2");

        // 写线程
        Thread r1 = new Thread(test::writer);
        Thread r2 = new Thread(test::writer);
        Thread r3 = new Thread(test::writerOther);
        r1.setName("写线程1");
        r2.setName("写线程2");
        r3.setName("写线程3");



        // 读读不互斥
        /*t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("消耗时间(ms)：" + (System.currentTimeMillis() - currentTime));*/

        // 读写互斥 读线程拿到锁之后阻塞2秒，写线程拿到锁阻塞10秒
        // 根据有序性，假设t1 读锁先去拿到锁之后阻塞2秒，那么此时必然是写锁是会在读锁释放锁之后才能进行执行，因此执行时间必然大于10
        //t1.start();
        //r1.start();

        // 写读有序性
        /*r1.start();
        t1.start();
        t1.join();
        r1.join();
        System.out.println("消耗时间(ms)：" + (System.currentTimeMillis() - currentTime));*/


        // 写写互斥
        r3.start();
        r1.start();
        r1.join();
        r3.join();
        System.out.println("消耗时间(ms)：" + (System.currentTimeMillis() - currentTime));
        System.out.println(map.get("zhoucg"));

    }


}
