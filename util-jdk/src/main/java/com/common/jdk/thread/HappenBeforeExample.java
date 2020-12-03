package com.common.jdk.thread;

/**
 * java本地内存的数据什么时候刷新到主内存
 * https://www.oschina.net/question/3410045_2312979
 * @author zhoucg
 * @date 2020-12-01 14:13
 */
public class HappenBeforeExample {

    private static volatile boolean keepRunning = true;
    public static void main(String[] args) throws InterruptedException {


        new Thread(() -> {
            while (keepRunning) {
                // doSomething
                int i=0;
                i++;
            }
            System.out.println("停止循环");

        }).start();

        Thread.sleep(500);
        keepRunning = false;
        System.out.println("改变停止标志");
    }
}
