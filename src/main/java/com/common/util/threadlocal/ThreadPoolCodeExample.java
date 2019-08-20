package com.common.util.threadlocal;


import com.common.util.thread.ThreadCatchExample;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: zhoucg
 * @date: 2019-08-08
 */
public class ThreadPoolCodeExample {

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    private static final int COUNT_BITS = Integer.SIZE - 3; //

    private static final int RUNNING    = -1 << COUNT_BITS;

    private static int ctlOf(int rs, int wc) { return rs | wc; }

    public static void main(String[] args) {

        /*int count_bits = Integer.SIZE - 3;
        System.out.println(count_bits);
        int running = -1 >> count_bits;
        System.out.println(running);

        AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

        int c = ctl.get();
        System.out.println(c);*/

        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 50, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(50), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("reject submit");
            }
        });

        for(int i=0;i<1000;i++) {
            executor.submit(() -> {
                System.out.println(Thread.currentThread().getName()+"线程信息：" + executor);
                    }
            );
        }

        /*for(int i=0;i<100;i++) {
            Future<String> task = executor.submit(() -> {
                System.out.println("当前队列数据信息："+executor);
                return "SUCCESS" + Thread.currentThread().getName();
            });
            String res = "";
            try{
                res = task.get();
            } catch (Exception e) {
                System.out.println("error");
            }
            System.out.println(res);

        }*/




    }
}
