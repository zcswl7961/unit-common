package com.common.jdk.collect;

import java.util.HashMap;

/**
 * @author zhoucg
 * @date 2020-12-17 21:16
 */
public class HashMapDemo {


    public static void main(String[] args) {
        HashMapDemo hashMapDemo = new HashMapDemo();
        int i = hashMapDemo.hashCode();
        int hash = hash(hashMapDemo);
        System.out.println(i);
        System.out.println(hash);


        int i1 = 1 ^ 2;
        int i2 = 1 >>> 2;
        System.out.println(i2);
        System.out.println(i1);


        int i3 = (16 - 1) & 120;
        int i4 = 120 % 16;
        System.out.println(i3);
        System.out.println(i4);

    }

    static final int hash(Object key) {
        int h;
        //
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}
