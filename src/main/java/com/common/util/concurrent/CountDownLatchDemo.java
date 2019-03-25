package com.common.util.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhoucg on 2019-03-25.
 * CountDownLatch类位于java.util.concurrent包下，利用它可以实现类似计数器的功能。
 * 比如有一个任务A，它要等待其他4个任务执行完毕之后才能执行，此时就可以利用CountDownLatch来实现这种功能了。
 */
public class CountDownLatchDemo implements Runnable{

    static final CountDownLatch end = new CountDownLatch(10);

    static final CountDownLatchDemo countDownLatchDemo = new CountDownLatchDemo();


    @Override
    public void run() {
         try{
             //模拟检查任务
             Thread.sleep(new Random().nextInt(10) * 100);
             System.out.println("Current thread check complete");
             end.countDown();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++) {
            executorService.submit(countDownLatchDemo);
        }
        end.await();
        System.out.println("over");
        executorService.shutdown();
        //等待检查
    }
}
