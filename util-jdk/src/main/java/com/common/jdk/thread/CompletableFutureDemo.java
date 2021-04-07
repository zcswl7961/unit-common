package com.common.jdk.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author zhoucg
 * @date 2021-04-03 11:41
 */
public class CompletableFutureDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(5);
        demo1(executor);
    }


    public static void demo1(Executor executor) throws ExecutionException, InterruptedException {
        // 创建异步对象
        // 1、runXxxx 都是没有返回结果的，supplyXxxx都是可以获取返回结果的
        //
        System.out.println("主线程开始执行。。。");
        CompletableFuture<Void> future01 = CompletableFuture.runAsync(() -> System.out.println("无返回值，使用默认线程池"));
        System.out.println(future01.get());

        CompletableFuture<Void> future02 = CompletableFuture.runAsync(() -> System.out.println("无返回值，使用自定义线程池"), executor);
        System.out.println(future02.get());

        CompletableFuture<Long> future03 = CompletableFuture.supplyAsync(() -> {
            System.out.println("有返回值，使用默认线程池");
            return System.currentTimeMillis();
        });
        System.out.println(future03.get());

        CompletableFuture<Long> future04 = CompletableFuture.supplyAsync(() -> {
            System.out.println("有返回值，使用自定义线程池");
            return System.currentTimeMillis();
        }, executor);
        System.out.println(future04.get());

        System.out.println("主线程结束。。。");


        // 异步任务完成时回调方法
        // 1, 返回相同的结果或例外，这一阶段的新completionstage，这个阶段完成时，执行特定动作的结果（或 null如果没有）和异常（或 null如果没有）这个阶段。
        //public CompletableFuture<T> whenComplete(BiConsumer<? super T,? super Throwable> action);
    }

    public static void demo2(Executor executor) throws ExecutionException, InterruptedException {

        System.out.println("主线程开始执行。。。线程id：" + Thread.currentThread().getId());


        CompletableFuture<Long> future01 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future01执行，线程id：" + Thread.currentThread().getId());
            return System.currentTimeMillis();
        }).whenComplete((res, e) -> {
            System.out.println("future01回调方法执行 ==》" + res + "，线程id：" + Thread.currentThread().getId());
        });
        System.out.println(future01.get());

        CompletableFuture<Long> future02 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future02执行，线程id：" + Thread.currentThread().getId());
            return System.currentTimeMillis();
        }).whenCompleteAsync((res, e) -> {
            System.out.println("future02回调方法执行 ==》" + res + "，线程id：" + Thread.currentThread().getId());
        });
        System.out.println(future02.get());

        CompletableFuture<Long> future03 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future03执行，线程id：" + Thread.currentThread().getId());
            return System.currentTimeMillis();
        }).whenCompleteAsync((res, e) -> {
            System.out.println("future03回调方法执行 ==》" + res + "，线程id：" + Thread.currentThread().getId());
        }, executor);
        System.out.println(future03.get());

        CompletableFuture<Long> future04 = CompletableFuture.supplyAsync(() -> {
            System.out.println("future04执行，线程id：" + Thread.currentThread().getId());
            int i = 10 / 0;
            return System.currentTimeMillis();
        }).whenCompleteAsync((res, e) -> {
            System.out.println("future04回调方法执行 ==》" + res + "，线程id：" + Thread.currentThread().getId());
        }, executor).exceptionally((e) -> {
            System.out.println("异常回调执行，异常原因：" + e.getMessage() + "==> 线程id：" + Thread.currentThread().getId());
            return 0L;
        });
        System.out.println(future04.get());

        System.out.println("主线程结束。。。");
    }





}
