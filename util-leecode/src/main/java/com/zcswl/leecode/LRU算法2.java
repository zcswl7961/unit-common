package com.zcswl.leecode;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 双端链表的策略实现LRU，最近最少使用的策略
 * head next prev
 * @author zhoucg
 * @date 2021-04-07 16:24
 */
public class LRU算法2 {

    /**
     * 双端队列
     */
    private static class DoubleLinkedNode {
        int key;
        int value;
        DoubleLinkedNode next;
        DoubleLinkedNode prev;
        public DoubleLinkedNode() {}
        public DoubleLinkedNode(int key, int value) {
            this.key = key;
            this.value = value;
        }

    }

    private DoubleLinkedNode head, tail;

    private static Map<Integer, DoubleLinkedNode> cache;
    int size;
    int capacity;


    public LRU算法2(int capacity) {
        // 初始化对应的容量
        cache = new ConcurrentHashMap<>();
        head = new DoubleLinkedNode();
        tail = new DoubleLinkedNode();
        head.next = tail;
        tail.prev = head;
        this.capacity = capacity;
    }

    public int get(int key) {
        DoubleLinkedNode doubleLinkedNode = cache.get(key);
        if (doubleLinkedNode == null) {
            return -1;
        }
        // 移动到头部
        moveToHead(doubleLinkedNode);
        return doubleLinkedNode.value;
    }

    public void put(int key, int value) {
        DoubleLinkedNode doubleLinkedNode = cache.get(key);
        if (doubleLinkedNode != null) {
            doubleLinkedNode.value = value;
            moveToHead(doubleLinkedNode);
        } else {
            // 表示不存在，
            size++;
            DoubleLinkedNode insert = new DoubleLinkedNode(key, value);
            cache.put(key, insert);
            // 移动到表头操作
            moveToHead(doubleLinkedNode);
            // 检测对应的阈值操作
            if (size > capacity) {
                deleteTail();
                --size;
            }
        }
    }

    private void deleteTail() {
        DoubleLinkedNode deleteNode = tail.prev;
        tail.prev = deleteNode.prev;
        deleteNode.prev.next = tail;
        cache.remove(deleteNode.key);
    }

    private void moveToHead(DoubleLinkedNode node) {
        node.prev = head;
        head.next = node;
        node.next = head.next;
        head.next.prev = node;
    }

}
