package com.common.jdk.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoucg
 * @date 2021-01-22 10:58
 */
public class ScheduleThreadPoolExecutorDemo {


    public static void main(String[] args) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

        // 执行第一个任务
        Runnable runnable = () -> {System.out.println(Thread.currentThread().getName() + "执行一定时间后的操作");};
        for (int i = 0; i < 10 ; i++) {
            scheduledThreadPoolExecutor.scheduleWithFixedDelay(runnable,0,5, TimeUnit.SECONDS);
        }

    }
}
