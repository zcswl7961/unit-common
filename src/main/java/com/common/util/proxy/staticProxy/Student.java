package com.common.util.proxy.staticProxy;

/**
 * Created by zhoucg on 2019-04-18.
 * 被代理类
 */
public class Student implements Person{

    private String name;

    Student(String name) {
        this.name = name;
    }

    @Override
    public void giveMoney() {
        System.out.println(this.name + " 上交了50元");
    }
}
