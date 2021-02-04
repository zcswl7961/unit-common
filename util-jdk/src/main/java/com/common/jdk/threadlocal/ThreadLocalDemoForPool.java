package com.common.jdk.threadlocal;

import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
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

    /**
     * 移除
     */
    public void remove() {
        num.remove();
    }


    public static void main(String[] args) {
        // 定义了三个核心线程
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ThreadLocalDemoForPool threadLocalDemoForPool = new ThreadLocalDemoForPool();
        for (int i = 0;i < 10 ;i++) {
            Task task = new Task(threadLocalDemoForPool);
            executorService.execute(task);
        }
    }


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
