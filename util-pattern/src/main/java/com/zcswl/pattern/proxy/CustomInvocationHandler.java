package com.zcswl.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zhoucg on 2019-03-17.
 * 动态代理类需要实现InvocationHandler
 */
public class CustomInvocationHandler implements InvocationHandler {

    private Object target ;

    public CustomInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * 设置被动态代理的类的增强方法
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("equals")) {
            Object target = this.target;
            Object objects = args[0];
            Proxy proxy1 = (Proxy) objects;
            System.out.println(111);
        }

        System.out.println("Before invocation==============");
        // target代理的是对应调用的本身
        Object retVal = method.invoke(target,args);

        // 这里得proxy就是代理对象 下面得这个写法错误
        // method.invoke(proxy,args);
        System.out.println("After invocation===============");
        return retVal;

    }
}
