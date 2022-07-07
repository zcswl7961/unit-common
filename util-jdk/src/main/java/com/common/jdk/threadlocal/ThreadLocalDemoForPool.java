package com.common.jdk.threadlocal;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 *
 * @author zhoucg
 * @date 2021-02-03 19:17
 */
public class ThreadLocalDemoForPool {

    private static ThreadLocal<Integer>  num = ThreadLocal.withInitial(() -> 0);

    /**
     * 获取下一个值
     */
    public int getNextNum() {
        num.set(num.get() + 1);
        return num.get();
    }

    /**
     * 获取当前值
     */
    public int get() {
        return num.get();
    }

    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }

    private static final int HASH_INCREMENT = 0x61c88647;

    private static AtomicInteger nextHashCode =
            new AtomicInteger();

    /**
     * 移除
     */
    public void remove() {
        num.remove();
    }


    public static void main(String[] args) {

        final int threadLocalHashCode = nextHashCode();
        System.out.println(threadLocalHashCode);
        final int threadLocalHashCode1 = nextHashCode();
        System.out.println(threadLocalHashCode1);
        final int threadLocalHashCode2 = nextHashCode();
        System.out.println(threadLocalHashCode2);

        // 定义了三个核心线程
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ThreadLocalDemoForPool threadLocalDemoForPool = new ThreadLocalDemoForPool();
        for (int i = 0;i < 10 ;i++) {
            Task task = new Task(threadLocalDemoForPool);
            executorService.execute(task);
        }
    }

    // key -> value               ThreadLocal
    // thread(name) -key ->value  Map

    private static class Task implements Runnable{

        private ThreadLocalDemoForPool threadLocalDemoForPool;

        public Task(ThreadLocalDemoForPool threadLocalDemoForPool) {
            this.threadLocalDemoForPool = threadLocalDemoForPool;
        }

        @SneakyThrows
        @Override
        public void run() {
            Random random = new Random();
            Thread.sleep(random.nextInt(1000));
            long threadId = Thread.currentThread().getId();
            int before = threadLocalDemoForPool.get();
            int after = threadLocalDemoForPool.getNextNum();
            System.out.println("当前线程执行次数：ThreadId:"+ threadId +" before："+ before + " ,after"+after);

        }
    }
}
