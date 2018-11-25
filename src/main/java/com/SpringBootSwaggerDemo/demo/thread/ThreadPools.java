package com.SpringBootSwaggerDemo.demo.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 *
 * @author zhoucg
 * @date 2018-11-25
 * alibaba 线程池的使用规范
 */
public class ThreadPools  {

    public static void main(String[] args) {

        /**
         * java线程池使用的正确方式
         *
         * 注意：最近阿里发布的 Java开发手册中强制线程池不允许使用 Executors 去创建，
         * 而是通过 ThreadPoolExecutor 的方式，这样的处理方式让写的同学更加明确线程池的运行规则，
         * 规避资源耗尽的风险。下面转载部分有较为详细的介绍
         */
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();

        ExecutorService executorService = new ThreadPoolExecutor(5,10,0L,
                TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(1024), namedThreadFactory,new ThreadPoolExecutor.AbortPolicy());


        executorService.execute(() ->{
            System.out.println(Thread.currentThread().getName());
        });
    }
}
