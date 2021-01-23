package com.common.jdk.thread;

/**
 * @author zhoucg
 * @date 2021-01-20 18:58
 */
public class TryFinallyDemo {


    public static void main(String[] args) {
        boolean workerStarted = false;
        try {
            // 我们模拟创建一个线程，并启动错误的线程信息
            Runnable runnable = () -> {
                try {
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                throw new RuntimeException("报错lalalal");};
            Thread thread = new Thread(runnable);
            thread.start();
            workerStarted = true;


        } finally {
            if(!workerStarted) {
                System.out.println("报错");
            } else {
                System.out.println("没有报错");
            }
        }
    }
}
