package com.zcswl.leecode;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU 最近最少使用的操作
 * LinkedHashMap 双端链表的方式
 * @author zhoucg
 * @date 2021-04-07 16:17
 */
public class LRUCache extends LinkedHashMap<Integer, Integer> {
    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key,value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}
