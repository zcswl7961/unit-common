package com.common.util.threadlocal;

/**
 * Created by zhoucg on 2019-02-15.
 *
 * ThreadLocal 实例使用
 */
public class ThreadLocalPoolExample {


    public static class MyRunnable implements Runnable {


        /**
         * 实例对象
         */
        private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

        @Override
        public void run() {
            threadLocal.set( (int) (Math.random() * 100D) );

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }

            System.out.println(Thread.currentThread().getName()+":"+threadLocal.get());
        }
    }

    public  static void main(String[] args) throws Exception {
        MyRunnable sharedRunnableInstance = new MyRunnable();

        Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

    }

}
