package com.common.jdk.thread;

/**
 * Created by zhoucg on 2019-01-31.
 *
 * 线程死锁
 */
public class ThreadLockedExample {


    static class LockedRunnable implements Runnable {

        Object obj1;
        Object obj2;

        public LockedRunnable(Object obj1,Object obj2) {
            this.obj1 = obj1;
            this.obj2 = obj2;
        }


        @Override
        public void run() {
            synchronized (obj1) {
                String name = Thread.currentThread().getName();
                System.out.println(name);
                System.out.println("第一条");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (obj2) {
                    System.out.println("第二条");
                }
            }
        }
    }

    /**
     *  分析：
     *   1.线程死锁是有两个线程各自占有的对象锁是对方想要的，两个线程相互抢占对象锁，而自己又不能释放自己占有的对象锁，造成的相互等待的状态
     *   2.代码原理：
     *      1).创建两个线程，嵌套两个同步代码块。两个对象锁
     *      2).先让线程走线程1进入第一次同步代码处于等待状态，等待第二个线程也进入他的第一个同步代码块里面，这时第一个线程下面的同步代码块的对象锁被第二个线程占用，第二个同理，处于同时等待状态
     */
    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = new Object();
        new Thread(new LockedRunnable(obj1 ,obj2)).start();
        new Thread(new LockedRunnable(obj2 ,obj1)).start();
    }

}


