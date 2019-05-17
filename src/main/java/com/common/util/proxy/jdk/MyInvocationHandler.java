package com.common.util.proxy.jdk;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: zhoucg
 * @date: 2019-05-17
 */
public class MyInvocationHandler implements InvocationHandler {

    private final TestAddBean testAddBean;

    public MyInvocationHandler(TestAddBean testAddBean) {
        this.testAddBean = testAddBean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        testAddBean.test(methodName);
        return null;
    }
}
