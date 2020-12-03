package com.common.jdk.thread;


import org.checkerframework.checker.units.qual.A;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Doug Lea 大神之作  AbstractQueuedSynchronizer
 * @author zhoucg
 * @date 2020-12-01 14:48
 */
public class AQSExample {


    private AQSExample example;

    /**
     * 重要的实现：
     * ReentrantLock
     * CountDownLatch
     * Semaphore
     * FutureTask
     */

    public static void main(String[] args) {
        AQSExample aqsExample = new AQSExample();
        AQSExample example = aqsExample;
        System.out.println(example == aqsExample);
        aqsExample = new AQSExample();

        System.out.println(example == aqsExample);

        // ReentrantLock 实现 ReentrantLockDemo

        int sum = 100;
        if (!test(sum) && test3(100)) {
            System.out.println("1");
        }


        // 首先分析一下公平锁
        ReentrantLock reentrantLock = new ReentrantLock(true);
        reentrantLock.lock();

        try {
            System.out.println("测试结果");
        } finally {
            reentrantLock.unlock();
        }
    }


    public static boolean test (int sum) {
        return sum %2 == 0;
    }

    public static boolean test3(int sum1) {
        System.out.println("sum1：" +sum1);
        return true;
    }

}
