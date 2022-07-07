package com.common.jdk.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * https://zhuanlan.zhihu.com/p/166188567
 * @author xingyi
 * @date 2022/7/5
 */
public class LockSupportDemo2 {

    public static void main(String[] args) {
        Thread parkThread = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println(Thread.currentThread().getName() + "进入park");
                LockSupport.park(this);
                System.out.println(Thread.currentThread().getName() + "解除park");
            }
        }, "parkThread");
        parkThread.start();

        // 方法1，通过LockSupport.unpark
        Thread unParkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "进入unPark");
                LockSupport.unpark(parkThread);
                System.out.println(Thread.currentThread().getName() + "退出unPark");
            }
        }, "unParkThread");

        unParkThread.start();


        // 方法2，第二种方式通过thread.interrupt()
        Thread unParkThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "进入中断");
                parkThread.interrupt();
                System.out.println(Thread.currentThread().getName() + "退出中断");
            }
        }, "unParkThread");

        unParkThread2.start();
    }
}
