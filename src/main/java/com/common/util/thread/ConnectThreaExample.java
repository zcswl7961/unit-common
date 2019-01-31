package com.common.util.thread;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import scala.tools.cmd.gen.AnyVals;

/**
 * Created by zhoucg on 2019-01-31.
 * 线程间的通信
 *
 * 线程通信的目标是使线程间能够相互发送信号，另一个方面，线程通信使线程能够等待其他线程的信号
 *
 * java线程间通信的主题：
 * 1，通过共享对象通信
 * 2，忙等待
 * 3，wait（），notify（），notifyAll()
 * 4，丢失的信号，
 * 5，假唤醒
 * 6，多线成等待相同信号
 * 7，不要对常量字符串或全局对象调用wait（）
 */
public class ConnectThreaExample {


    /**
     * 1，通过共享对象通信
     * 线程间发送信号的一个简单方式是在共享对象的变量里设置信号值。线程 A 在一个同步块里设置 boolean 型成员变量
     * hasDataToProcess 为 true，线程 B 也在同步块里读取 hasDataToProcess 这个成员变量。
     * 这个简单的例子使用了一个持有信号的对象，并提供了 set 和 check 方法:
     */
    class MySignal {
        protected boolean hasDataToProcess = true;

        public synchronized boolean hasDataToProcess() {
            return this.hasDataToProcess;
        }

        public synchronized void setHasDataToProcess(boolean signal) {
            this.hasDataToProcess = signal;
        }
    }


    /**
     * 2，忙等待
     * 准备处理数据的线程B正在等待数据变为可用，换句话说，它在等待线程A的一个信号，这个信号使
     * hasDataToProcess（） 返回true，线程B运行在一个循环里，以等待这个信号
     */

    protected static MySignal mySignal;


//        while(!mySignal.hasDataToProcess) {
//            // do something
//        }


    /**
     * 3，忙等待没有对运行等待线程的CPU进行有效的利用，除非平均等待的时间非常的短，否则，让等待线程进入睡眠或者非运行
     * 状态更好，知道它受到它等待的信号
     *
     * Java 有一个内建的等待机制来允许线程在等待信号的时候变为非运行状态。java.lang.Object 类定义了三个方法，
     * wait()、notify()和 notifyAll()来实现这个等待机制。
     *
     * 一个线程一旦调用了任意对象的wait（）方法，就会变成非运行状态，知道另一个线程调用了同一个对象的
     * notidy（）方法，为了调用wait（）或者notidy（）方法，线程必须先获得那个对象的锁，也就是说，线程必须在同步快
     * 里调用wait（）或者notify（）方法，
     */

    static class MonitorObject {}

    static class MyWaitNotidy {
        MonitorObject myMonitorObject = new MonitorObject();

        boolean wasSignalled = false;


        /**
         * 等待线程将调用doWait()，而唤醒的线程将调用doNotify（）,
         * 这种问题会导致信号的丢失，即唤醒的方法先调用等待的方法之后，会导致等待的方法一致等待
         * 使用信号类的方式
         */
        public void doWait() {
            synchronized (myMonitorObject) {

                while(!wasSignalled) { //这种做法要慎重，目前的 JVM 实现自旋会消耗 CPU，如果长时间不调用 doNotify 方法，doWait 方法会一直自旋，CPU 会消耗太大
                    try{
                        System.out.println(Thread.currentThread().getName()+" 线程正在等待");
                        myMonitorObject.wait();
                        System.out.println(Thread.currentThread().getName()+ " 线程被唤醒了");
                    } catch ( Exception e) {

                    }
                }
                //
                wasSignalled = true;

            }
        }

        public void doNotify() {
            synchronized (myMonitorObject) {
                wasSignalled = true;
                System.out.println(Thread.currentThread().getName() + " 线程调用的唤醒方法");
                myMonitorObject.notify();
            }
        }

    }

    static class MyThread implements Runnable {

        public MyThread() {}

        protected MyWaitNotidy myWaitNotidy;
        protected String flag;

        public MyThread(MyWaitNotidy myWaitNotidy,String flag) {
            this.myWaitNotidy = myWaitNotidy;
            this.flag = flag;
        }

        @Override
        public void run() {

            if("1".equals(flag)) {
                myWaitNotidy.doWait();
            }
            else if ("2".equals(flag)) {
                try{
                    System.out.println("唤醒线程休眠6秒");
                    Thread.sleep(6000); //释放CPU的执行权，释放锁
                } catch ( Exception e){
                    System.out.println("唤醒线程报错");
                }

                myWaitNotidy.doNotify();
            }
        }
    }



    public static void main(String[] args) throws Exception{

        MyWaitNotidy myWaitNotidy = new MyWaitNotidy();
        Thread thread1 =new Thread(new MyThread(myWaitNotidy,"1"));
        Thread thread2 =new Thread(new MyThread(myWaitNotidy,"2"));


        thread2.start();
        thread1.start();
        thread2.join();


        System.out.println("主线程结束");


    }








}
