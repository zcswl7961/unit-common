package com.common.jdk.thread;


/**
 * 线程中断：
 * 线程的thread.interrupt()方法是中断线程，将会设置该线程的中断状态位，即设置为true，
 * 中断的结果线程是死亡、还是等待新的任务或是继续运行至下一步，就取决于这个程序本身。线程会不时地检测这个中断标示位，
 * 以判断线程是否应该被中断（中断标示值是否为true）。它并不像stop方法那样会中断一个正在运行的线程。
 * @author zhoucg
 * @date 2020-11-30 21:51
 */
public class ThreadInterrupted {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new LocalThread();

        t.start();
        Thread.sleep(200);
        // 给对应的t线程发送一个暂停的命令
        t.interrupt();
        t.join();

        // 获取对应的当前线程的结果
        System.out.println(LocalThread.currentFlag);
    }


    public static class LocalThread extends Thread {

        private static volatile int currentFlag= 0;

        @Override
        public void run() {
            while(true) {
                currentFlag++;
                if (Thread.interrupted()) {
                    System.out.println("当前线程被暂停啦");
                    break;
                }
                System.out.println("获取了上一个的执行结果：" + currentFlag);
                currentFlag++;
            }
        }
    }

}
