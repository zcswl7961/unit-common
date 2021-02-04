package com.common.jdk.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * https://www.jianshu.com/p/f1f2cd289205
 * @author zhoucg
 * @date 2020-12-02 17:00
 */
public class LockSupportDemo {

    public static Object u = new Object();


    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");


    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super(name);
        }
        @Override public void run() {
            // park是不会释放锁的
            //synchronized (u) {
                System.out.println("in " + getName());
                LockSupport.park();
                boolean interrupted = Thread.interrupted();
                System.out.println(interrupted);
//                if (Thread.currentThread().isInterrupted()) {
//                    System.out.println("被中断了");
//                }
                System.out.println("继续执行");
           // }
        }
    }


    public static class ThreadTest extends Thread{

        @Override
        public void run() {
            System.out.println("线程进入执行：ThreadTest");
            LockSupport.park();

            System.out.println("运行结束");
            System.out.println("是否中断:"+Thread.currentThread().isInterrupted());
            super.run();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ThreadTest threadTest = new ThreadTest();
        threadTest.start();
        System.out.println("主线程结束");
        // 给一个线程的中断标志位，会使其park（）阻塞的线程继续执行
        Thread.sleep(1000L);
        //threadTest.interrupt();

        // 同样，我们可以调用LockSupport.unPark(Thread thread)进行唤醒操作
        LockSupport.unpark(threadTest);
        // 当LockSupport.park()在持有锁的语句种，不会释放锁




//        t1.start();
//        Thread.sleep(1000L);
//        t2.start();
//        Thread.sleep(3000L);
//        t1.interrupt();
//        //LockSupport.unpark(t2);
//        t1.join();
//        t2.join();


        // test interrupt
//        t1.start();
//        Thread.sleep(5000);
//        LockSupport.unpark(t1);
        //t1.interrupt();



//        Object ob = new Object();
//
//
//        Thread current = new Thread() {
//
//            @Override
//            public void run() {
//                System.out.println("current ThreadName:" + Thread.currentThread().getName() + " 启动了");
//                LockSupport.park(this);
//                System.out.println("又可以开始啦");
//            }
//        };
//
//        current.start();
//        Thread.sleep(5000);
//
//        LockSupport.unpark(current);


//        Thread thread = new Thread(() -> {
//            System.out.println("执行线程");
//            LockSupport.park();
//            System.out.println(Thread.currentThread().getName() + "被唤醒");
//        });
//        thread.start();
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        LockSupport.unpark(thread);









    }
}
