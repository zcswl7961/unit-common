package com.zcswl.pattern.proxy;

/**
 * Created by zhoucg on 2019-03-17.
 * jdk 动态代理的实现类
 */
public class HelloTestImpl implements HelloTest{
    @Override
    public int say(String name) {
        System.out.println("Hello:" + name);
        return 0;
    }
}
