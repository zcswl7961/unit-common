package com.common.util.concurrent;

/**
 * Created by zhoucg on 2019-03-24.
 * 指令重排
 *
 * 如果one 线程再 other线程之前执行 x=0 y =1
 * 如果one 线程再 other线程之后执行 x=1,y=0
 * 如果one线程和 other线程交替执行 x=1 y=1
 */
public class PossibleReordering {
    static int x =0,y=0; // x=0 y =1  x=1,y=0 x=1 y=1
    static int a =0,b=0; // a =1 b=1  a=1 b=1 a=1 b=1

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(new Runnable() {
          public void run() {
              a = 1;
              x = b;
              /**
               * 指令重排
               * x=b
               * a=1
               */
          }
        });
        Thread other = new Thread(new Runnable() {
         public void run() {
             b = 1;
             y = a;
             /**
              * b=1
              * y=a
              */
         }
     });
         one.start();other.start();
         one.join();other.join();
         System.out.println("(" + x + "," + y + ")");
    }
}
