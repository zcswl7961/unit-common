package com.common.jdk.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子和非原则自增操作
 * @author zhoucg
 * @date 2020-12-04 13:48
 */
public class AtomicIntegerExample {

    /**
     * 非原子性操作的值
     */
    private static int notCasFlag = 0;

    /**
     * 原子操作
     */
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = () -> {
            for (int i = 0; i < 10000 ;i ++) {
                notCasFlag ++;
                atomicInteger.getAndIncrement();
            }
        };

        Thread current1 = new Thread(runnable);
        Thread current2 = new Thread(runnable);

        current1.start();
        current2.start();

        current1.join();
        current2.join();

        System.out.println("非原子操作的值: " + notCasFlag);
        System.out.println("原子操作的值: " + atomicInteger.get());



    }
}
