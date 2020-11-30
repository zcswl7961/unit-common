package com.common.jdk.desginpattern.callback;

/**
 * @author zhoucg
 * @date 2020-11-24 10:24
 */
public interface FetcherCallback {

    void onData(Data data) throws Exception;

    void onError(Throwable throwable);
}
