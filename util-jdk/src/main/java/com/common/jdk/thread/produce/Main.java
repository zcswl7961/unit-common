package com.common.jdk.thread.produce;

/**
 * @author zhoucg
 * @date 2021-01-18 17:14
 */
public class Main {

    public static void main(String[] args) {

        AResource ar = new AResource();
        // 这个表示的是生产者的任务
        Producer pr = new Producer(ar);
        // 这个表示的是消费者的任务
        Consumer con = new Consumer(ar);
        new Thread(pr).start();
        new Thread(pr).start();// 两个生产者的线程，其实是一个生产者的任务

        new Thread(con).start();
        new Thread(con).start();// 两个消费者的线程，其实是一个消费者的任务
    }
}

// 定义生产者
class Producer implements Runnable {
    private AResource ar;

    public Producer(AResource ar) {
        this.ar = ar;
    }

    @Override
    public void run() {
        while (true) {
            ar.set1("馒头");
        }
    }

}


// 定义消费者
class Consumer implements Runnable {
    private AResource ar;

    public Consumer(AResource ar) {
        this.ar = ar;
    }

    @Override
    public void run() {
        while (true) {
            ar.eat();
        }
    }

}
