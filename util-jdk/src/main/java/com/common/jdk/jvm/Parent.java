package com.common.jdk.jvm;

/**
 * @author zhoucg
 * @date 2020-10-16 15:02
 */
public class Parent {

    {
        // 3
        System.out.println("父类Parent中的实例构造块");
    }

    static {
        // 1
        System.out.println("父类Parent中的静态代码块");
    }

    Parent() {
        // 4
        System.out.println("父类Parent中的构造函数");
    }
}
