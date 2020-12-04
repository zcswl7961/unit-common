package com.common.jdk.thread.concurrent;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author zhoucg
 * @date 2020-11-23 18:56
 */
public class ExecutorServiceDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

//        System.out.println("当前提交的事件：" + System.currentTimeMillis());
//        Future<String> submit = executorService.submit(() -> {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                throw e;
//            }
//            return "返回了结果了";
//        });
//        // 阻塞式
//        String s = submit.get();
//        System.out.println("返回的结果:" + s + " 返回的事件：" + System.currentTimeMillis());
//
//
//        Future<?> test = executorService.submit(() -> System.out.println("test"));
//        Object o = test.get();
//
//
//        Future<String> submit1 = executorService.submit(() -> System.out.println("有参数的"), "ZHOUCG");
//        String s1 = submit1.get();


        Runnable runnable = () -> {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程阻塞了五秒种");
        };

        Runnable runnable1 = () -> {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程阻塞了八秒种");
        };

        List<Future<Boolean>> futures = new ArrayList<>();
        futures.add(executorService.submit(runnable, true));
        futures.add(executorService.submit(runnable1, true));

        for (Future<Boolean> future : futures) {
            boolean done = future.isDone();
            Boolean aBoolean = future.get();
            System.out.println(aBoolean);
        }

    }
}
