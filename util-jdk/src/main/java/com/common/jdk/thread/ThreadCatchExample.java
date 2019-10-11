package com.common.jdk.thread;

/**
 * Created by zhoucg on 2019-02-21.
 * setDefaultUncaughtExceptionHandler作用相当于一个全局的catch。一般情况下用于记录当程序发生你未捕获的异常的时候，
 * 调用一个你默认的handler来进行某些操作，比如记录客户端版本，异常信息，等客户信息，方便收集异常原因。
 */
public class ThreadCatchExample {

    public static void main(String[] args) {

//        Thread t = new Thread(new AdminThread());
//        t.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
//        t.start();

       String topic =  "RT.JMB.CONFIG".indexOf(".") > -1 ? "RT.JMB.CONFIG".substring(0,"RT.JMB.CONFIG".indexOf(".")) : "RT.JMB.CONFIG";
        System.out.println(topic);
    }

    static class AdminThread implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                System.out.println("start ... Exception");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new NullPointerException(); //直接exception
        }
    }

    static class ExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) { //调用此方法来进行，对异常处理，需要实现UncaughtExceptionHandler 接口
            System.out.println("Thread:" + t + " Exception message:" + e);
        }
    }
}
