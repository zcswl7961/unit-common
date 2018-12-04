package com.common.util;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.HashMap;

/**
 * Created by zhoucg on 2018-11-14.
 */
public class HashMapTest {


    public static void main(String[] args) {


        HashMap<String,String>  map = new HashMap<>();


        map.put("key","zhouchengogng");


        String b = map.get("value");
        System.out.println(b);


//        int a = 106078;
//        int b = 15;
//
//
//        int c = a & b;
//
//        System.out.println(c);


        String  test = "value";

//
//        int b;
//        int c = (test == null) ? 0 : (b = test.hashCode()) ^ (b >>> 16);
//
//
//        int finalTest = c & 14;
//
//        System.out.println(finalTest);
//
//        System.out.println(c);

    }
}
