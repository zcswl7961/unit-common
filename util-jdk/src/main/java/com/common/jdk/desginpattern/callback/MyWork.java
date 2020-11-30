package com.common.jdk.desginpattern.callback;

/**
 * @author zhoucg
 * @date 2020-11-24 10:27
 */
public class MyWork {

    public void doWork() {
        Fetcher fetcher = new MyFetcher(new Data(1, 0));
        fetcher.fetchData(new FetcherCallback() {
            @Override
            public void onError(Throwable cause) {
                System.out.println("An error accour: " + cause.getMessage());
            }

            @Override
            public void onData(Data data) {
                 System.out.println("Data received: " + data);
            }
        });
    }

    public static void main(String[] args) {
        MyWork w = new MyWork();
        w.doWork();
    }
}
