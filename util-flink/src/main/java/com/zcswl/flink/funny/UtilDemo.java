package com.zcswl.flink.funny;

import org.apache.flink.api.java.Utils;

/**
 * tests from {@link org.apache.flink.api.java.Utils}
 * @author zhoucg
 * @date 2021-02-22 18:24
 */
public class UtilDemo {

    // error
    // Utils 内部含有了 @Internal 的注解
    public static void main(String[] args) {
        String callLocationName = Utils.getCallLocationName();
        System.out.println(callLocationName);
    }
}
