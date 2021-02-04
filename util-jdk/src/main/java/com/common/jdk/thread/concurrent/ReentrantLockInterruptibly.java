package com.common.jdk.thread.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock中的lockInterruptibly（） 方法
 * 当两个线程同时通过lock.lockInterruptibly()获取某个锁时，假若此时线程A获取到了锁，而线程B只有等待，
 * 那么对线程B调用threadB.interrupt()方法能够中断线程B的等待过程。
 *
 *
 * ReentrantLock.lockInterruptibly允许在等待时由其它线程调用等待线程的Thread.interrupt方法来中断等待线程的等待而直接返回，这时不用获取锁，而会抛出一个InterruptedException。
 * ReentrantLock.lock方法不允许Thread.interrupt中断,即使检测到Thread.isInterrupted,一样会继续尝试获取锁，失败则继续休眠。只是在最后获取锁成功后再把当前线程置为interrupted状态,然后再中断线程。
 * @author zhoucg
 * @date 2021-01-22 16:09
 */
public class ReentrantLockInterruptibly {


    private Lock lock = new ReentrantLock();

    public void doBussiness() {
        String name = Thread.currentThread().getName();

        try {
            System.out.println(name + " 开始获取锁");
            lock.lock();
            //lock.lockInterruptibly();
            System.out.println(name + " 得到锁");
            System.out.println(name + " 开工干活");
            for (int i=0; i<5; i++) {
                Thread.sleep(1000);
                System.out.println(name + " : " + i);
            }
        } catch (InterruptedException e) {
            System.out.println(name + " 被中断");
            System.out.println(name + " 做些别的事情");
        } finally {
            try {
                lock.unlock();
                System.out.println(name + " 释放锁");
            } catch (Exception e) {
                System.out.println(name + " : 没有得到锁的线程运行结束");
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {

        ReentrantLockInterruptibly lockTest = new ReentrantLockInterruptibly();

        Thread t0 = new Thread(lockTest::doBussiness);
        Thread t1 = new Thread(lockTest::doBussiness);

        // 启动线程t1
        t0.start();
        Thread.sleep(10);
        // 启动线程t2
        t1.start();
        t1.interrupt();
    }
}
