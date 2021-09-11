package com.zcswl.google.guice;

/**
 * @author xingyi
 * @date 2021/9/11
 */
public class DefaultService2 implements Service{
    @Override
    public String guiceHello() {
        System.out.println("say guice2 hello");
        return "2";
    }
}
