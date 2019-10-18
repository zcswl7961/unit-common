package com.common.jdk.classPackage;

/**
 * 字节码.class文件研究
 * @author: zhoucg
 * @date: 2019-10-16
 */
public class ByteCodeDemo {

    private int a = 1;

    public int add() {
        int b = 2;
        int c = a+b;
        System.out.println(c);
        return c;
    }

}
