package com.common.jdk.thread;

import java.util.concurrent.Exchanger;

/**
 * 线程切换：https://mp.weixin.qq.com/s/GzjcfogxK3beXB1II7VoQw
 * @author zhoucg
 * @date 2022-06-11 21:37
 */
public class ExchangerDemo {

    private static void testExchange() {

        // 创建一个线程交换的对象Exchanger
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(() -> {
            String data = "线程对象的数据1";
            System.out.println(Thread.currentThread().getName() + data);

            // 交换数据
            try {
                data = exchanger.exchange(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + data);

        }).start();

        new Thread(() -> {
            String data = "线程对象的数据2";
            System.out.println(Thread.currentThread().getName() + data);

            // 交换数据
            try {
                data = exchanger.exchange(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + data);

        }).start();

    }

    public static void main(String[] args) {
        testExchange();
    }

}
