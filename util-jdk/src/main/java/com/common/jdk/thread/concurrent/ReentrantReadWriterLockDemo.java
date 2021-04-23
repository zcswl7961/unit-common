package com.common.jdk.thread.concurrent;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock 分为读锁和写锁，读锁是共享锁，
 * 可被多个线程同时使用，写锁是独占锁。持有写锁的线程可以继续获取读锁，反之不行。
 *
 * 读写锁：
 * 读锁是共享锁，
 * 写锁是独占锁
 * 持有写锁的线程能够获取到读锁
 * 持有读锁的线程获取不了对应的写锁
 * @author zhoucg
 * @date 2021-04-23 13:14
 */
public class ReentrantReadWriterLockDemo {


    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        final CacheDemo cache = new CacheDemo();
        final Random random = new Random();

        for (int i = 0; i < 30; i++)
        {
            executor.execute(new Runnable()
            {
                @Override
                public void run()
                {
                    for (int j = 0; j < 10; j++)
                    {
                        String key = "" + random.nextInt(2);
                        cache.getData(key.trim());
                    }
                }
            });
        }
        executor.shutdown();
    }

    /**
     * 官方API给的写法
     */
    private static class CachedData {
        Object data;
        volatile boolean cachedValid;

        final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();


        void processCachedData() {
            // 获取读锁
            rwl.readLock().lock();
            if (!cachedValid) { // 如果缓存过期了，或者为 null
                // 释放掉读锁，然后获取写锁 (后面会看到，没释放掉读锁就获取写锁，会发生死锁情况)
                rwl.readLock().unlock();
                rwl.writeLock().lock();

                try {
                    if (!cachedValid) { // 重新判断，因为在等待写锁的过程中，可能前面有其他写线程执行过了
                        data = new Object();
                        cachedValid = true;
                    }
                    // 获取读锁 (持有写锁的情况下，是允许获取读锁的，称为 “锁降级”，反之不行。)
                    rwl.readLock().lock();
                } finally {
                    // 释放写锁，此时还剩一个读锁
                    rwl.writeLock().unlock(); // Unlock write, still hold read
                }
            }

            try {
                use(data);
            } finally {
                // 释放读锁
                rwl.readLock().unlock();
            }
        }


        public void use(Object use) {
            System.out.println(use);
        }
    }


    static class CacheDemo {

        private final Map<String, Object> cache = new ConcurrentHashMap<>();
        private final ReadWriteLock rwl = new ReentrantReadWriteLock();
        private final Lock r = rwl.readLock();
        private final Lock w = rwl.writeLock();
        private final Random random = new Random();


        public Object getData(String key) {
            // 对于任务而言，加上读锁操作
            r.lock();
            Object value = null;
            try {
                value = cache.get(key);
                if (value == null) {
                    // 释放对应的读锁
                    // 持有读锁的线程获取不到对应的写锁，需要释放之后
                    r.unlock();
                    // 加入写锁
                    w.lock();
                    try {
                        value = cache.get(key);
                        if (value == null) {
                            slowly();
                            value = random.nextInt(1000);
                            System.out.println(Thread.currentThread().getName() + " produces value "
                                    + value + ", for key " + key);
                            cache.put(key, value);
                        }
                        // 持有对应的写锁之后可以直接获取对应的读锁
                        r.lock();
                    } finally {
                        w.unlock();
                    }
                }
            } finally {
                r.unlock();
            }
            System.out.println(Thread.currentThread().getName() + " gets value " + value + " , for key " + key);
            return value;
        }

        private void slowly(){
            try
            {
                Thread.sleep(1);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
