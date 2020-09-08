package com.common.guava;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.Iterator;
import java.util.Set;

/**
 *
 * 可以用两种方式看待Multiset：
 *
 * 没有元素顺序限制的ArrayList<E>
 * Map<E, Integer>，键为元素，值为计数
 *
 * 新集合类
 * @author zhoucg
 * @date 2020-09-07 16:14
 */
public class MutiDemo {


    public static void main(String[] args) {
        HashMultiset<String> hashMultiset = HashMultiset.create();
        hashMultiset.add("周成功");
        hashMultiset.add("wl");
        hashMultiset.add("周成功");


        int count = hashMultiset.count("周成功");
        Preconditions.checkArgument(count == 2);


        int size = hashMultiset.size();
        Preconditions.checkArgument(size == 3);


        hashMultiset.remove("周成功");
        Preconditions.checkArgument(hashMultiset.size() == 2);

        hashMultiset.add("周成功",5);
        int currentCount = hashMultiset.count("周成功");
        Preconditions.checkArgument(currentCount == 6);


        Iterator iterator = hashMultiset.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            System.out.println(next);
        }

        // 所有Multiset实现的内存消耗随着不重复元素的个数线性增长。
        Set<Multiset.Entry<String>> entries = hashMultiset.entrySet();
        for (Object object : entries) {
            System.out.println(object);
        }

    }
}
