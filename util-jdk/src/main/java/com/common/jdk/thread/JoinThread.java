package com.common.jdk.thread;

/**
 * Created by zhoucg on 2018-11-22.
 */
public class JoinThread {


    /**
     * 程序设计，t1,t2,t3三个线程，保持三个线程同时执行，并且让t1,t2执行完之后t3才执行
     *
     * join()方法：
     *          当某个程序执行流中调用其他线程的join方法，调用线程被阻塞，直到新join的线程执行完。
     *          假设现在有两个线程A、B。如果在A的run方法中调用B.join()，表示A需要在B线程上面等待，也就是需要在B线程执行完成之后才能再次执行
     * @param args
     */
    public static void main(String[] args) throws Exception{
        /**
         * 线程t1
         */
        Thread t1 = new Thread(() -> {
            try{
                System.out.println("Thread t1 is running...");
                Thread.sleep(1000);
            } catch ( Exception e ) {

            }
        });

        /**
         * 线程t2
         */
        Thread t2 = new Thread(() ->{
           try{
               System.out.println("Thread t2 is running...");
               Thread.sleep(1000);
           } catch ( Exception e ){

           }
        });

        /**
         * 线程t3
         */
        Thread t3 = new Thread(() ->{
           try{
               System.out.println("Thread t3 is running...");
               Thread.sleep(1000);
           } catch ( Exception e) {

           }
        });

//        t1.start();
//        t1.join(); //只会使主线程进入等待状态并等待t1线程执行完毕之后才被唤醒
//
//        t2.start();
//        t2.join();
//
//        t3.start();


        t1.start();
        t1.join();

        t2.start();

        Thread.sleep(1000);
        System.out.println("thread main is running ...");

}
}
