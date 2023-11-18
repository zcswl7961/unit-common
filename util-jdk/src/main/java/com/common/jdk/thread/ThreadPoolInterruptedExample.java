package com.common.jdk.thread;

/**
 * https://blog.csdn.net/daobuxinzi/article/details/124967466
 * https://www.jb51.net/article/230252.htm
 * @author zhoucg
 * @date 2023-10-28 22:32
 */
public class ThreadPoolInterruptedExample {

    public static void main(String[] args) throws InterruptedException {
        // sleepThread睡眠1000ms
        final Thread sleepThread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i< 1000000; i++) {
                    System.out.println("i==="+i);
                }
            }
        };
        // busyThread一直执行死循环
        Thread busyThread = new Thread() {
            @Override
            public void run() {
                while (true);
            }
        };
        sleepThread.start();
        busyThread.start();


        sleepThread.interrupt(); // 改变中断状态标志位
        busyThread.interrupt();
        System.out.println(busyThread.isInterrupted());


        //while (sleepThread.isInterrupted()); // 一直死循环等待该中断标志位被改变才继续执行下面的代码
        System.out.println("sleepThread isInterrupted: " + sleepThread.isInterrupted());
        System.out.println("sleepThread isInterrupted: " + sleepThread.isInterrupted());
        System.out.println("busyThread isInterrupted: " + busyThread.isInterrupted());
    }

}
