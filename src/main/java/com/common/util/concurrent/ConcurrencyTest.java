package com.common.util.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by zhoucg on 2018-11-20.
 */
public class ConcurrencyTest {



    /**
     * 请求总数
     */
    public static int clientTotal = 5000;

    /**
     * 同时并发执行的线程数
     */
    public static int threadTotal = 200;

    public static int count = 0;

    public static void main(String[] args) throws Exception{
        //定义线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //定义信号量,给出允许并发的线程数目
        final Semaphore semaphore = new Semaphore(threadTotal);
        //统计计数结果
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        //将请求放入线程池
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    //信号量的获取
                    semaphore.acquire();
                    add();
                    //释放
                    semaphore.release();
                } catch (Exception e) {
                   e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        //关闭线程池
        executorService.shutdown();
        System.out.println("count="+count);
    }
    /**
     * 统计方法
     */
    private static void add() {
        count++;
    }

}
