package com.common.jdk.thread.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author zhoucg
 * @date 2021-01-23 19:56
 */
public class ForkJoinPoolDemo {


    static List<String> urls = new ArrayList<String>() {
        {
            add("http://www.baidu.com");
            add("http://www.sina.com");
            add("http://www.baidu.com");
            add("http://www.sina.com");
            add("http://www.baidu.com");
            add("http://www.sina.com");
            add("http://www.baidu.com");
            add("http://www.sina.com");
            add("http://www.baidu.com");
            add("http://www.sina.com");
            add("http://www.baidu.com");
            add("http://www.sina.com");
            add("http://www.baidu.com");
            add("http://www.sina.com");
            add("http://www.baidu.com");
            add("http://www.sina.com");
            add("http://www.baidu.com");
            add("http://www.sina.com");
            add("http://www.baidu.com");
            add("http://www.sina.com");
            add("http://www.baidu.com");
            add("http://www.sina.com");
        }
    };


    // 本质是一个线程池,默认的线程数量:CPU的核数
    static ForkJoinPool forkJoinPool = new ForkJoinPool(3,
            ForkJoinPool.defaultForkJoinWorkerThreadFactory,
            null,
            true);
    public static String doRequest(String url,int index) {
        return index+" test " + url + "\n";
    }

    static class Job extends RecursiveTask<String> {
        List<String> urls;
        int start;
        int end;

        public Job(List<String> urls, int start, int end) {
            this.urls = urls;
            this.start = start;
            this.end = end;
        }

        @Override
        protected String compute() {
            int count = end - start;
            if (count <= 10) {
                String result = "";
                for (int i = start; i < end; i++) {
                    String s = doRequest(urls.get(i),i);
                    result = result + s;
                }
                return result;
            } else {
                int mid = (start + end) / 2;
                Job job1 = new Job(urls, start, mid);
                job1.fork();
                Job job2 = new Job(urls, mid, end);
                job2.fork();

                //固定写法
                String results = "";
                results += job1.join();
                results += job2.join();
                return results;
            }
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {


        Job job = new Job(urls, 0, urls.size());
        ForkJoinTask<String> forkJoinTask = forkJoinPool.submit(job);
        String s = forkJoinTask.get();
        System.out.println(s);
    }
}
