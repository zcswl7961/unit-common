package com.common.util.threadlocal;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 *
 * @author zhoucg
 * @date 2018-12-05
 * 线程池中使用ThreadLocal 导致内存泄漏的问题
 * {@link} https://www.jianshu.com/p/30ee77732843
 * {@link ThreadLocal} 源码 threadlocal.set()
 * threadlocal为什么使用弱引用？
 * 通过threadLocal,threadLocalMap,entry的引用关系看起来threadLocal存在内存泄漏的问题似乎是因为threadLocal是被弱引用修饰的。
 * 那为什么要使用弱引用呢？
 * 1，如果使用强引用
 *  假设threadLocal使用的是强引用，在业务代码中执行threadLocalInstance==null操作，以清理掉threadLocal实例的目的，
 *  但是因为threadLocalMap的Entry强引用threadLocal，
 *  因此在gc的时候进行可达性分析，threadLocal依然可达，对threadLocal并不会进行垃圾回收，这样就无法真正达到业务逻辑的目的，出现逻辑错误
 * 2，如果使用弱引用
 *  假设Entry弱引用threadLocal，尽管会出现内存泄漏的问题，但是在threadLocal的生命周期里（set,getEntry,remove）里，
 *  都会针对key为null的脏entry进行处理。
 *  从以上的分析可以看出，使用弱引用的话在threadLocal生命周期里会尽可能的保证不出现内存泄漏的问题，达到安全的状态。
 */
public class ThreadPoolTest {

    static class LocalVariable{
        private Long[] a = new Long[1024 * 1024];
    }
    /**
     *  (1)
     */
    static ThreadFactory testThreadFactory = new ThreadFactoryBuilder().setNameFormat("test-pool-%d").build();
    static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,5,
            1L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(1024),testThreadFactory,new ThreadPoolExecutor.DiscardPolicy());

    /**
     * (2)
     */
    final static ThreadLocal<LocalVariable> LOCAL_THREADLOCAL = new ThreadLocal<>();

    public static void main(String[] args) throws Exception{
        /**
         * (3)
         */
        for(int i=0;i<50 ;i++) {
            poolExecutor.execute(() ->{
                LOCAL_THREADLOCAL.set(new LocalVariable());
                System.out.println(Thread.currentThread().getName()+":"+"use local variable");

                //去掉对应的线程引用
                LocalVariable localVariable = LOCAL_THREADLOCAL.get();

                System.out.println("localVariable="+localVariable);

                /**
                 * 及时进行当前线程的threadlocal的值得删除
                 */
                LOCAL_THREADLOCAL.remove();
            });
        }

        // (6)
        System.out.println("pool execute over");

        poolExecutor.shutdown();

    }

}
