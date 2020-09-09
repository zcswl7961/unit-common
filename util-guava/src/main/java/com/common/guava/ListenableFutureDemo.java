package com.common.guava;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;

/**
 *
 * ListenableFuture 包解析
 *
 * @author zhoucg
 * @date 2020-09-08 16:24
 */
public class ListenableFutureDemo {

    public static void main(String[] args) {
        // ListenableFutureTask通过静态create方法返回实例，还有一个重载方法，不太常用
        ListenableFutureTask<String> task = ListenableFutureTask.create(() -> "ZHOOUCG");
        // 启动任务
        new Thread(task).start();


        task.addListener(() -> System.out.println("线程执行"), MoreExecutors.directExecutor());



        // 真正干活的线程池操作
        ThreadPoolExecutor  poolExecutor = new ThreadPoolExecutor(
                5,
                5,
                0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new CustomizableThreadFactory("demo"),
                new ThreadPoolExecutor.DiscardPolicy());

        // guava的接口ListeningExecutorService继承了jdk原生ExecutorService接口，重写了submit方法，修改返回值类型为ListenableFuture
        ListeningExecutorService listeningExecutor = MoreExecutors.listeningDecorator(poolExecutor);


        // 获得一个随着jvm关闭而关闭的线程池，通过Runtime.getRuntime().addShutdownHook(hook)实现
        // 修改ThreadFactory为创建守护线程，默认jvm关闭时最多等待120秒关闭线程池，重载方法可以设置时间
        ExecutorService newPoolExecutor = MoreExecutors.getExitingExecutorService(poolExecutor);


        // 只增加关闭线程池的钩子，不改变ThreadFactory
        MoreExecutors.addDelayedShutdownHook(poolExecutor, 120, TimeUnit.SECONDS);


        //像线程池提交任务，并得到ListenableFuture
        ListenableFuture<String> listenableFuture = listeningExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "";
            }
        });
    }
}
