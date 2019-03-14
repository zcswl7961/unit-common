package com.common.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by zhoucg on 2019-03-08.
 * 自定义一个BeanPostProcessor
 */
public class MyBeanPostProcessor implements BeanPostProcessor{

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("user")) {
            System.out.println("这个是执行之前的操作");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("user")) {
            System.out.println("这个是执行之后的操作");
        }
        return null;
    }
}
