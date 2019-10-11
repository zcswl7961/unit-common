package com.common.concurrent.cas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by zhoucg on 2019-03-25.
 * 信号量，Semaphore可以控同时访问的线程个数，通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
 */
public class SemphoreDemo implements Runnable{

    final Semaphore semaphore = new Semaphore(5);

    @Override
    public void run() {

        try{
            semaphore.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+" Done !");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }

    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        final SemphoreDemo semphoreDemo = new SemphoreDemo();
        for(int i=0;i<20;i++) {
            System.out.println(executorService);
            executorService.submit(semphoreDemo);
        }
    }
}
