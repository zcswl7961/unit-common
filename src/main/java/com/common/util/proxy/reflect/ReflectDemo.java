package com.common.util.proxy.reflect;

/**
 * @author: zhoucg
 * @date: 2019-05-20
 */
public class ReflectDemo {

    public String className;

    private String classType;

    public ReflectDemo() {

    }

    private ReflectDemo(String className) {
        this.className = className;
    }

    public void print() {
        System.out.println("显示结果："+className);
    }
}
