package com.common.util.guava;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * JDK8中关于Future的进一步的优化
 * <p>所谓异步调用其实就是实现一个可无需等待被调用函数的返回值而让操作继续运行的方法。在 Java 语言中，
 * 简单的讲就是另启一个线程来完成调用中的部分计算，使调用继续运行或返回，而不需要等待计算结果。
 * 但调用者仍需要取线程的计算结果。
 *
 * <p>JDK5新增了Future接口，用于描述一个异步计算的结果。虽然 Future 以及相关使用方法提供了异步执
 * 行任务的能力，但是对于结果的获取却是很不方便，只能通过阻塞或者轮询的方式得到任务的结果。
 * 阻塞的方式显然和我们的异步编程的初衷相违背，轮询的方式又会耗费无谓的 CPU 资源，而且也不能及时地得到计算结果。
 * 这个是Future最大的不方便之处
 *
 * <p>
 *
 * @author: zhoucg
 * @date: 2019-05-31
 */
public class CompletableFutureUtil {

    @Test
    public void testFuture () throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        // <T> Future<T> submit(Callable<T> task);
        Future<Integer> future = service.submit(() -> {
            try{
                Thread.sleep(50000);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        });

        System.out.println(future.get());
        System.out.println("done");
    }

    @Test
    public void test1(){
        long start = System.currentTimeMillis();
        // 结果集
        List<String> list = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Integer> taskList = Arrays.asList(2, 1, 3, 4, 5, 6, 7, 8, 9, 10);
        // 全流式处理转换成CompletableFuture[]+组装成一个无返回值CompletableFuture，join等待执行完毕。返回结果whenComplete获取
        CompletableFuture[] cfs = taskList.stream()
                .map(integer -> CompletableFuture.supplyAsync(() -> calc(integer), executorService)
                        .thenApply(h->Integer.toString(h))
                        .whenComplete((s, e) -> {
                            System.out.println("任务"+s+"完成!result="+s+"，异常 e="+e+","+new Date());
                            list.add(s);
                        })
                ).toArray(CompletableFuture[]::new);
        // 封装后无返回值，必须自己whenComplete()获取
        CompletableFuture.allOf(cfs).join();
        System.out.println("list="+list+",耗时="+(System.currentTimeMillis()-start));
    }

    public int calc(Integer i) {
        try {
            if (i == 1) {
                Thread.sleep(3000);//任务1耗时3秒
            } else if (i == 5) {
                Thread.sleep(5000);//任务5耗时5秒
            } else {
                Thread.sleep(1000);//其它任务耗时1秒
            }
            System.out.println("task线程：" + Thread.currentThread().getName()
                    + "任务i=" + i + ",完成！+" + new Date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }
}
