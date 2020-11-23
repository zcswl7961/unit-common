package com.common.jdk.threadlocal;

/**
 * @author zhoucg
 * @date 2020-11-22 21:42
 */
public class ThreadPoolDefaultCoreNumsTest {


    public static void main(String[] args) {
        // 为什么是CPU核心数 * 2 这个数值设置
        int i = Runtime.getRuntime().availableProcessors() * 2;
        System.out.println(i);
    }
}

