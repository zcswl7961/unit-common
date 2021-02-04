package com.common.jdk.thread.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ArrayBlockingQueue demo
 * @author zhoucg
 * @date 2021-02-01 10:51
 */
public class ArrayBlockingQueueDemo {

    private static final BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = () -> {
            System.out.println("阻塞性的取出队列的值的结果");
            try {
                while (true) {
                    String take = queue.take();
                    Thread.sleep(1000L);
                    System.out.println(take);
                }
            } catch (InterruptedException e) {
                System.out.println("被其他线程中断操作");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(1000L);

        for (int i = 0; i < 10;i++) {
            // 阻塞性的加入队列，
            queue.put("XXXA"+i);
        }

        // 给一个中断标志
        //thread.interrupt();

    }
}
