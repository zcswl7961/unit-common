package com.common.util.spring.aop;

/**
 * Created by zhoucg on 2019-04-18.
 *
 * pointcut：定义这些需要捕捉的方法，通常是不止一种方法
 *
 *
 * idea 通过 file-->Settings-->Builder,Execution,Deployment-->Compiler-->java Compiler
 * 的方式设置Ajc的编译器
 */
public aspect MyAspectJDemo {

    pointcut recordLog():call (* HelloAspectJ.sayHello(..));

    pointcut authCheck():call (* HelloAspectJ.sayHello(..));

    before():authCheck() {
        System.out.println("sayHello方法执行前权限验证");
    }

    after():recordLog(){
        System.out.println("sayHello方法执行后记录日志");
    }

}
