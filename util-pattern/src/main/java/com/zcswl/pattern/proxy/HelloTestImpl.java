package com.zcswl.pattern.proxy;

/**
 * Created by zhoucg on 2019-03-17.
 * jdk 动态代理的实现类
 */
public class HelloTestImpl implements HelloTest{
    @Override
    public int say(String name) {
        System.out.println("Hello:" + name);
        this.hello();
        return 0;
    }

    @Override
    public String hello() {
        System.out.println("你好，zhoucg");
        return "zhoucg";
    }


    /**
     * 代理不了
     */
    public static String staticMethod() {
        System.out.println("返回这个静态方法");
        return "ZHOUCG";
    }
}
