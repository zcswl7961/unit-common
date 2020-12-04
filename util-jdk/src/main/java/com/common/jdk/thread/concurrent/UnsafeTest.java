package com.common.jdk.thread.concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 *
 * jdk中sun.misc.Unsafe的使用，这是一个不稳定并且不安全的类，
 * 它提供了能对存入内存的对应的内部的数据获取
 *      如获取该对象分配到内存中属性的内存偏移地址数据
 * 同时提供了cas 等操作
 * 底层操作
 * @author zhoucg
 * @date 2018-12-10
 */
public class UnsafeTest {

    private static Unsafe unsafe;

    public static long valueOffset;

    private volatile int value = 1;

    static {
        try{
            Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeInstance.setAccessible(true);
            unsafe = (Unsafe) theUnsafeInstance.get(Unsafe.class);

            if(unsafe != null) {
                System.out.println(unsafe);
            }
            valueOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("value"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long getValueOffset() {
        return valueOffset;
    }

    public static void setValueOffset(long valueOffset) {
        UnsafeTest.valueOffset = valueOffset;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static void main(String[] args) {


        System.out.println(UnsafeTest.valueOffset);

    }
}
