package com.common.jdk.desginpattern.proxy;

/**
 * <p>
 *     接口代理
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 9:24
 */
public class Proxy implements Subject{

    private RealSubject realSubject;

    @Override
    public void request() {

        if(realSubject == null) {
            realSubject = new RealSubject();
        }
        preRequest();
        realSubject.request();
        postRequest();
    }

    public void preRequest()
    {
        System.out.println("访问真实主题之前的预处理。");
    }
    public void postRequest()
    {
        System.out.println("访问真实主题之后的后续处理。");
    }
}
