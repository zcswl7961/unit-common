package com.common.util.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhoucg
 * @date: 2019-08-15
 */
public class HashMapTest {

    public static void main(String[] args) {
        Map<String,String> maps = new HashMap();
        maps.put("1","1");
        maps.put("2","22");
        Collection<String> values = maps.values();
        values.stream().forEach(value -> {
            System.out.println(value);
        });
    }
}
