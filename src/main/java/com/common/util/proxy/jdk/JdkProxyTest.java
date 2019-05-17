package com.common.util.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * @author: zhoucg
 * @date: 2019-05-17
 */
public class JdkProxyTest {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        TestAddBean testAddBean = new TestAddBean();

        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(testAddBean);

        //获取对应的ProxyBean的对象
        ProxyBean proxyBean = (ProxyBean) Proxy.newProxyInstance(ProxyBean.class.getClassLoader(),new Class<?>[] {ProxyBean.class},myInvocationHandler);

        proxyBean.say();


    }
}
