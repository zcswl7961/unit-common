package com.common.util.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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

        System.out.println("Before invocation");
        Object retVal = method.invoke(target,args);
        System.out.println("After invocation");
        return retVal;
    }
}
