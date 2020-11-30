package com.common.jdk.desginpattern.callback;

/**
 * @author zhoucg
 * @date 2020-11-24 10:26
 */
public class MyFetcher implements Fetcher{

    final Data data;

    public MyFetcher(Data data) {
        this.data = data;
    }


    @Override
    public void fetchData(FetcherCallback fetcherCallback) {
        try {
            fetcherCallback.onData(data);
        } catch (Exception e) {
            fetcherCallback.onError(e);
        }
    }
}
