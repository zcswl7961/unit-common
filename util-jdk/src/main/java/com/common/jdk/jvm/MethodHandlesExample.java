package com.common.jdk.jvm;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author zhoucg
 * @date 2020-11-13 22:14
 */
public class MethodHandlesExample {

    /**
     * 这个会报错
     * @param args
     * @throws Throwable
     */
    public static void main(String[] args) throws Throwable {
        MethodTypeTest methodTypeTest = new MethodTypeTest();
        String zhoucg = methodTypeTest.test("zhoucg");
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        // 查找MethodTypeTest.test 方法
        MethodType methodType = MethodType.methodType(String.class, String.class);

        MethodHandle virtual = lookup.findVirtual(MethodTypeTest.class, "test", methodType);
        Object o = lookup.findVirtual(MethodTypeTest.class, "test", methodType).invoke("wl0");

        System.out.println(o);


    }
}
