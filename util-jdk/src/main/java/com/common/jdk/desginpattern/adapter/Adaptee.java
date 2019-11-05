package com.common.jdk.desginpattern.adapter;

/**
 * <p>
 *     适配者接口
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 9:29
 */
public class Adaptee {

    public void specificRequest(){
        System.out.println("适配者中的业务代码被调用！");
    }
}
