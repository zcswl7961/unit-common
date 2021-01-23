package com.common.jdk.thread.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 *
 * Condition配合ReentrantLock进行使用
 * https://www.cnblogs.com/gemine/p/9039012.html
 * 首先我们需要明白condition对象是依赖于lock对象的，意思就是说condition对象需要通过lock对象进行创建出来
 * (调用Lock对象的newCondition()方法)。consition的使用方式非常的简单。但是需要注意在调用方法前获取锁。
 * @author zhoucg
 * @date 2021-01-22 18:54
 */
public class ConditionUseCase {

    public Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();

    public static void main(String[] args) {

        ConditionUseCase useCase = new ConditionUseCase();
        // 创建一个固定线程池的线程数
        ExecutorService executorService = Executors.newFixedThreadPool (2);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                useCase.conditionWait();
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                useCase.conditionSignal();
            }
        });

    }


    public void conditionWait()  {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "拿到锁了");
            System.out.println(Thread.currentThread().getName() + "等待信号");
            condition.await();
            System.out.println(Thread.currentThread().getName() + "拿到信号");
        }catch (Exception e){

        }finally {
            lock.unlock();
        }
    }
    public void conditionSignal() {
        lock.lock();
        try {
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + "拿到锁了");
            condition.signal();
            System.out.println(Thread.currentThread().getName() + "发出信号");
        }catch (Exception e){

        }finally {
            lock.unlock();
        }
    }



}
