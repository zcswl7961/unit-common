package com.common.util.proxy.jdk;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * @author: zhoucg
 * @date: 2019-05-17
 */
public class JdkProxyTest {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        TestAddBean testAddBean = new TestAddBean();
        MyInvocationHandler myInvocationHandler = new MyInvocationHandler(testAddBean);

        //获取对应的ProxyBean接口的代理对象
        ProxyBean proxyBean = (ProxyBean) Proxy.newProxyInstance(ProxyBean.class.getClassLoader(),new Class<?>[] {ProxyBean.class},myInvocationHandler);
        proxyBean.say();


        //获取到代理对象的Class对象
        Class proxyClass = Proxy.getProxyClass(ProxyBean.class.getClassLoader(),ProxyBean.class);
        //根据Proxy.newProxyInstance(ClassLoader,Class<?>[] interfaces,InvocationHandler)获取代理对象
        ProxyBean proxyClassBean = (ProxyBean) Proxy.newProxyInstance(proxyClass.getClassLoader(),proxyClass.getInterfaces(),myInvocationHandler);
        proxyClassBean.say();
        //根据获取到的代理的Class对象实例化
        Constructor constructors  = proxyClass.getConstructor(InvocationHandler.class);
        System.out.println("aa"+constructors);
        ProxyBean constructorProxyBean = (ProxyBean) constructors.newInstance(myInvocationHandler);
        constructorProxyBean.say();


    }
}
