package com.common.jdk.thread.concurrent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhoucg on 2019-03-29.
 * ConcurrentHashMap初始化时，传递一个initialCapacity值，该值会在初始化的时候转成对应的2 n次方的值
 */
public class ConcurrentHashMapDemo {


    public static void main(String[] args) {

        ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap<>(2);

        concurrentHashMap.put("concurrentHashMap","1");
        concurrentHashMap.put("concurrentHashMap1","1");
        concurrentHashMap.put("concurrentHashMap2","1");
        concurrentHashMap.put("concurrentHashMap3","1");
        concurrentHashMap.put("concurrentHashMap4","1");
        concurrentHashMap.put("concurrentHashMap5","1");
        concurrentHashMap.put("concurrentHashMap6","1");



    }
}
