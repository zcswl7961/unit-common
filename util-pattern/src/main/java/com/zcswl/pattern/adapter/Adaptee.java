package com.zcswl.pattern.adapter;

/**
 * 适配者接口
 * @author zhoucg
 * @date 2019-12-10 16:24
 */
public class Adaptee {

    public void specificRequest()
    {
        System.out.println("适配者中的业务代码被调用！");
    }
}
