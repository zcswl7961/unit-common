package com.common.jdk.desginpattern.proxy;

/**
 * <p>
 *     真实主题实现类
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 9:22
 */
public class RealSubject implements Subject{
    @Override
    public void request() {

        System.out.println("访问真实主题");

    }
}
