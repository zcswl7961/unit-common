package com.common.jdk.thread;

/**
 * wait 和notify实例
 * @author zhoucg
 * @date 2020-12-01 9:56
 */
public class ThreadWaitNotify {

    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {

        MyThread1 myThread1 = new MyThread1();
        MyThread2 myThread2 = new MyThread2();

        myThread1.start();
        Thread.sleep(30000);
        myThread2.start();

    }

    public static class MyThread1 extends Thread {

        @Override
        public void run() {
            int currentFlag = 0;
            synchronized (LOCK) {
                for (int i =0;i<10000000; i++) {
                    currentFlag++;
                }
                System.out.println(Thread.currentThread().getName() + " : 执行了： currentFlag:" +currentFlag);
                try {
                    // 当前暂停，其他线程执行notify() 获取notifyAll()的时候的继续向下执行
                    LOCK.wait();
                    System.out.println("接着向下执行，执行操作结果");
                    for (int i =0 ;i < 1000000; i++) {
                        currentFlag++;
                    }
                    System.out.println("最后的结果：" + currentFlag);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class MyThread2 extends Thread {

        @Override
        public void run() {
            synchronized (LOCK) {
                LOCK.notify();
            }
        }
    }
}
