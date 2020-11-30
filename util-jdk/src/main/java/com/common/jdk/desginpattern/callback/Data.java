package com.common.jdk.desginpattern.callback;

/**
 * @author zhoucg
 * @date 2020-11-24 10:24
 */
public class Data {

    private int n;
    private int m;

    public Data(int n, int m) {
        this.n = n;
        this.m = m;
    }

    @Override
    public String toString() {
        int r = n/m;
        return n + "/" + m +" = " + r;
    }
}
