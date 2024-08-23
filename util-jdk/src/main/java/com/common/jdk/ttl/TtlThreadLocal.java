package com.common.jdk.ttl;

/**
 *
 *
 * @author xingyi
 * @date 2023/6/21
 */
public class TtlThreadLocal {

    public static void main(String[] args) {
        ThreadLocal<String> parent = new ThreadLocal<>();

        parent.set(Thread.currentThread().getName() + "=======hello,myThread00");

        new Thread(() -> {
            try {
                // dosomething
                Thread.sleep(3000);
                // 使用线程变量
                System.out.println(Thread.currentThread().getName() + ":" + parent.get());
                // 清除
                parent.remove();
                // do other thing
                //.....
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "thread-1").start();

        new Thread(() -> {
            try {
                // 设置本线程变量
                parent.set(Thread.currentThread().getName() + "=======hello,myThread02");
                // dosomething
                Thread.sleep(4000);
                // 使用线程变量
                System.out.println(Thread.currentThread().getName() + ":" + parent.get());
                // 清除
                parent.remove();
                // do other thing
                //.....
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "thread-2").start();

        System.out.println(Thread.currentThread().getName() + ":" + parent.get());
    }
}
