package com.common.jdk.thread.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by zhoucg on 2019-03-25.
 * 信号量，Semaphore可以控同时访问的线程个数，通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
 */
public class SemphoreDemo implements Runnable{

    final Semaphore semaphore = new Semaphore(1);

    @Override
    public void run() {

        try{
            int i = semaphore.availablePermits();
            System.out.println(i);
            semaphore.acquire();
            Random random = new Random();
            Thread.sleep(random.nextInt(3000));
            System.out.println("当前时间："+System.currentTimeMillis()+" 线程："+Thread.currentThread().getName()+" Done !");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            semaphore.release();
            semaphore.release();
            int i = semaphore.availablePermits();
            System.out.println(i);
        }

    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        final SemphoreDemo semphoreDemo = new SemphoreDemo();
        for(int i=0;i<1;i++) {
            System.out.println(executorService);
            executorService.submit(semphoreDemo);
        }
    }
}
