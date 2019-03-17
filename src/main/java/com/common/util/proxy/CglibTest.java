package com.common.util.proxy;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;

/**
 * Created by zhoucg on 2019-03-17.
 * cglib动态代理测试
 */
public class CglibTest {

    public static void main(String[] args) {

        // 该设置用于输出cglib动态代理产生的类,后面一个参数表示的存放class的文件目录
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\");

        HelloTest userDao = new HelloTestImpl();

        System.out.println("========普通对象调用方法========");
        userDao.say("普通方法");
        System.out.println("========代理对象增强方法========");

        Enhancer enhancer = new Enhancer();
        // 继承被代理类
        enhancer.setSuperclass(HelloTestImpl.class);
        // 设置回调
        enhancer.setCallback(new HelloInteceptor());
        // 创建代理类实例
        HelloTest proxy = (HelloTest) enhancer.create();

        // 创建代理对象
        proxy.say("cglib 动态代理");
    }

}
