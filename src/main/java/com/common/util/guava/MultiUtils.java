package com.common.util.guava;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import org.junit.Test;

/**
 * 关于MultiMap， Multiset，BiMap 的使用
 * BiMap的具体使用方式见：{@link BiMapDemo} BiMapDemo
 *
 *
 * Multiset的使用，统计使用
 * 集合[set]概念的延伸，它的元素可以重复出现…与集合[set]相同而与元组[tuple]相反的是，
 * Multiset元素的顺序是无关紧要的：Multiset {a, a, b}和{a, b, a}是相等的”。
 * ——译者注：这里所说的集合[set]是数学上的概念
 *
 * 关于Multiset
 * 1,没有元素顺序限制的ArrayList
 * 2,Map<E, Integer>，键为元素，值为计数
 * Map	对应的Multiset	是否支持null元素
 HashMap	HashMultiset	是
 TreeMap	TreeMultiset	是（如果comparator支持的话）
 LinkedHashMap	LinkedHashMultiset	是
 ConcurrentHashMap	ConcurrentHashMultiset	否
 ImmutableMap	ImmutableMultiset	否
 适用于对单一元素的计数
 */
public class MultiUtils {

    public static void main(String[] args) {


        HashMultiset<String> hashMultiset = HashMultiset.create();
        hashMultiset.add("1");
        hashMultiset.add("1");
        int a = hashMultiset.count("1");
        System.out.println(a);
    }

    /**
     * Multimap
     * key  string
     * value :Collections
     */
    @Test
    public void testMultiMap () {
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("a","b");
        multimap.put("a","c");
        System.out.println(multimap);
    }


}
