package com.common.jdk.collect;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhoucg
 * @date 2021-01-28 16:53
 */
public class ConcurrentHashMapNotSafeDemo implements Runnable{


    private static ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        scores.put("John", 0);
        Thread t1 = new Thread(new ConcurrentHashMapNotSafeDemo());
        Thread t2 = new Thread(new ConcurrentHashMapNotSafeDemo());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(scores);
    }


    /**
     * 线程不安全的案例，需要加上synchronized 保持线程安全
     */
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            synchronized (scores) {
                Integer score = scores.get("John");
                Integer newScore = score + 1;
                scores.put("John", newScore);
            }
        }
    }

    /**
     * 另一种解决方案,通过replace策略
     */
    public void runAnother() {
        for (int i = 0; i < 1000; i++) {
            // 自旋
            while (true) {
                Integer score = scores.get("John");
                Integer newScore = score + 1;
                boolean john = scores.replace("John", score, newScore);
                if (john) {
                    break;
                }
            }
        }
    }
}
