package com.zcswl.kafka.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 获取当前spring context上下文。
 *
 * <p>非spring容器所管理的bean可以通过该类获取spring 容器中的管理bean对象，以及获取spring 容器上下文
 * @author zhoucg
 * @date 2019-11-13 17:24
 */
@Slf4j
@Component
public final class SpringContextUtil implements ApplicationContextAware{

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext = applicationContext;
    }

    /**
     * 根据字节码获取spring 容器对象
     * @param clazz type the bean must match; can be an interface or superclass.
     * @return an instance of the single bean matching the required type
     */
    public static <T> T getBean(Class<T> clazz) {
        Assert.notNull(clazz,"current clazz must not be null");
        return applicationContext.getBean(clazz);
    }

    /**
     * 根据beanName获取spring 容器对象
     * @param beanName the name of the bean to retrieve
     * @return an instance of the bean
     */
    public static <T> T getBean(String beanName) {
        Assert.notNull(beanName,"current beanName must not be null");
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 返回当前Spring容器中beand对应的指定注解信息
     * @param beanName the name of the bean to look for annotations on
     * @param annotationType the annotation class to look for
     * @param <A> annotation
     * @return the annotation of the given type if found, or {@code null} otherwise
     */
    public static <A extends Annotation> A findAnnotationOnBean(String beanName,Class<A> annotationType) {
        Assert.notNull(annotationType,"annotationType must not be null");
        return applicationContext.findAnnotationOnBean(beanName,annotationType);
    }

    /**
     * 非spring 容器中的bean可以通过该方法获取spring 上下文对象:applicationContext
     */
    public static ApplicationContext getContext() {
        Assert.notNull(applicationContext,"There has no Spring ApplicationContext!");
        return applicationContext;
    }

    /**
     * 根据class字节码获取对应的类和子类集合
     * @param type the class or interface to match, or {@code null} for all concrete beans
     * @param <T> type
     * @return a Map with the matching beans, containing the bean names as keys and the corresponding bean instances as values
     */
    public static <T> Map<String, T> getBeansOfType(@Nullable Class<T> type) {
        Assert.notNull(type,"current type must not be null");
        return applicationContext.getBeansOfType(type);
    }









}
