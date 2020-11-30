package com.common.jdk.desginpattern.callback;

/**
 * @author zhoucg
 * @date 2020-11-24 10:23
 */
public interface Fetcher {

    void fetchData(FetcherCallback fetcherCallback);
}
