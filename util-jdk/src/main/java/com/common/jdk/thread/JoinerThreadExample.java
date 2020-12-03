package com.common.jdk.thread;

/**
 * Created by zhoucg on 2019-03-23.
 */
public class JoinerThreadExample {


    public volatile static int i=0;

    public static class AddThread extends Thread{

        @Override
        public void run() {
            for (i=0;i<1000000000;i++);
        }
    }

    public static void main(String[] args) throws Exception{
        AddThread ad = new AddThread();
        ad.start();
        ad.join();//当前线程等待ad线程结束，然后再一起执行下面的操作
        System.out.println("线程结束");
    }
}
