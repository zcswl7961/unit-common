package com.common.jdk.thread.concurrent;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

/**
 * https://blog.csdn.net/weixin_42146366/article/details/87822781
 * cas
 * AtomicReference和AtomicInteger非常类似，不同之处就在于AtomicInteger是对整数的封装，
 * 而AtomicReference则对应普通的对象引用。也就是它可以保证你在修改对象引用时的线程安全性。
 *
 * AtomicReference是作用是对”对象”进行原子操作。 提供了一种读和写都是原子性的对象引用变量。
 * 原子意味着多个线程试图改变同一个AtomicReference(例如比较和交换操作)将不会使得AtomicReference处于不一致的状态。
 * @author xingyi
 * @date 2021/11/17
 */
public class AtomicReferenceDemo {

    public static void main(String[] args) {
        AtomicReference<ObjectDemo> reference = new AtomicReference<>();
        ObjectDemo test = new ObjectDemo("zhoucg",21);
        reference.set(test);

        ObjectDemo objectDemo = reference.get();
        System.out.println(objectDemo);

        ObjectDemo test1 = new ObjectDemo("wl",21);
        reference.set(test1);
        System.out.println(reference.get());
        System.out.println(reference.get() == test1);


        reference.compareAndSet(test1, test);
        System.out.println(reference.get());
    }


    @Data
    @AllArgsConstructor
    private static class ObjectDemo {
        private String name;
        private int age;
    }
}
