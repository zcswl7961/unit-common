package com.common.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.PriorityOrdered;

/**
 * Created by zhoucg on 2019-03-14.
 * BeanDefinitionRegistryPostProcessor继承自BeanFactoryPostProcessor，是一种比较特殊的BeanFactoryPostProcessor。
 * BeanDefinitionRegistryPostProcessor中定义的postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)方法
 * 可以让我们实现自定义的注册bean定义的逻辑。下面的示例中就新定义了一个名为hello，类型为Hello的bean定义。
 */
public class CustomBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor ,PriorityOrdered {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        RootBeanDefinition peopleBean = new RootBeanDefinition(People.class);
        registry.registerBeanDefinition("people",peopleBean);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public int getOrder() {
        return 1;
    }
}
