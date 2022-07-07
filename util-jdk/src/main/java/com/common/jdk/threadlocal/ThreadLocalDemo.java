package com.common.jdk.threadlocal;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xingyi
 * @date 2022/6/13
 */
public class ThreadLocalDemo {

    // class-> threadLocal
    // gc -> gc root
    // weak reference - gc

    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }

    private static final int HASH_INCREMENT = 0x61c88647;

    private static AtomicInteger nextHashCode =
            new AtomicInteger();

    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

        final int threadLocalHashCode = nextHashCode();
        int i = threadLocalHashCode & 15;
        int i1 = nextHashCode() & 15;
        int i2 = nextHashCode() & 15;
        int i3 = nextHashCode() & 15;

        // gc - thread threadLocalMap  Entry K = null ,V = Object
        // se() .get() -> 垃圾回收  K = null Entry = null;
        // gc




        threadLocal.set(1);
        threadLocal.set(2);
        threadLocal.set(3);


        System.gc();
        Integer integer = threadLocal.get();
        //remove();




    }
}
