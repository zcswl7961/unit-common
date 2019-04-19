package com.common.util.spring.aop;

/**
 * Created by zhoucg on 2019-04-18.
 */
public class HelloAspectJ {

    public void sayHello() {
        System.out.println("hello word !");
    }

    public static void main(String[] args) {
        HelloAspectJ helloAspectJ = new HelloAspectJ();
        helloAspectJ.sayHello();
    }
}
