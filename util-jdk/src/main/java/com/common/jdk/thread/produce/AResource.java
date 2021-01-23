package com.common.jdk.thread.produce;

/**
 * 定义资源
 * @author zhoucg
 * @date 2021-01-18 17:12
 */
public class AResource {

    private String name;
    private int count = 1;
    private volatile boolean flag = false;


    public synchronized void set1(String name) {
        while (flag) {
            try {
                System.out.println(Thread.currentThread().getName() + "设置操作");
                wait();

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        this.name = name;
        count++;
        flag = true;
        notifyAll();
        System.out.println(Thread.currentThread().getName() + "....生产者..." + this.name + this.count);

    }


    public synchronized void eat() {
        // while是解决每一次启动线程都要去判断标记的方式
        if (!flag) {
            try {
                System.out.println(Thread.currentThread().getName() + "吃的操作");
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread().getName() + "....消费者..." + this.count);
        flag = false;
        notifyAll();
        // 这个方法是为了方式线程死锁的问题
    }


}
