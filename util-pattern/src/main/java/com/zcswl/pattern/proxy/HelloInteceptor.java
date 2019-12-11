package com.zcswl.pattern.proxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by zhoucg on 2019-03-17.
 * CGLIB代理不需要目标类实现接口，
 * 利用cglib动态代理生成字节码文件
 */
public class HelloInteceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(">>>>>>开启事务<<<<<<");
        Object proxyObject = methodProxy.invokeSuper(o, objects);
        System.out.println(">>>>>>关闭事务<<<<<<");
        return proxyObject;
    }
}
