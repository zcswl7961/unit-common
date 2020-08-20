package com.zcswl.spring.plugin;

import com.zcswl.spring.plugin.annotation.LocalReference;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * 基于BeanPostProcessor重构对应的bean实体
 *
 * @author zhoucg
 * @date 2020-03-12 16:02
 */
@Component
public class ConvertBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter{

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Class<?> aClass = bean.getClass();
        for (Field field : aClass.getDeclaredFields()) {

            if (field.isAnnotationPresent(LocalReference.class) && field.getAnnotation(LocalReference.class).flag()) {
                Class<?> declaringClass = field.getType();
                // now we should init proxy
                Object o = Proxy.newProxyInstance(declaringClass.getClassLoader(), new Class[]{declaringClass}, (proxy, method, args) -> "zhoucg");
                try {
                    field.setAccessible(true);
                    field.set(bean,o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }


        return bean;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return super.postProcessAfterInstantiation(bean, beanName);
    }
}
