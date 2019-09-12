package com.common.util.guava;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;

/**
 * @author: zhoucg
 * @date: 2019-08-31
 *
 * 限流算法：
 *  1，计数器算法
 *  2，漏桶算法
 *  3，令牌桶算法
 */
public class RateLimiterExample {


    public static void main(String[] args) throws InterruptedException {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(100));

        // 指定每秒放1个令牌
        RateLimiter limiter = RateLimiter.create(5);
        Thread.sleep(10000);

        /*for(int i=1;i<50;i++) {
            //请求RateLimiter，超过超过permits会被阻塞
            //acquire(int permits)函数主要用于获取permits个令牌，并计算需要等待多长时间，进而挂起等待，并将该值返回
            Double acquire = null;
            if(i==1) {
                acquire = limiter.acquire(1);
            } else if (i == 2) {
                acquire = limiter.acquire(10);
            } else if (i == 3) {
                acquire = limiter.acquire(2);
            } else if (i == 4) {
                acquire = limiter.acquire(20);
            } else {
                acquire = limiter.acquire(2);
            }
            //executorService.submit(new Task("获取令牌成功，获取耗：" + acquire + " 第 " + i + " 个任务执行"));
            System.out.println("获取令牌成功，获取耗：" + acquire + " 第 " + i + " 个任务执行");
        }*/

        while(true) {
            int randomAcquireNum = new Random().nextInt(5) +1;

            System.out.println("get "+randomAcquireNum+" tokens: " +limiter.acquire(randomAcquireNum)+ "s");
        }




    }

    static class Task implements Runnable {
        String str;
        public Task(String str) {
            this.str = str;
        }
        @Override
        public void run() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            System.out.println(sdf.format(new Date()) + " | " + Thread.currentThread().getName() + str);
        }
    }

}
