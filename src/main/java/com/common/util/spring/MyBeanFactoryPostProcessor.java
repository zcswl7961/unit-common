package com.common.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.StringValueResolver;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhoucg on 2019-03-14.
 * 自定义BeanFactoryPostProcessor
 * 实现过滤一下敏感字信息的功能，
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor{

    private Set<String> filterStrings;

    public MyBeanFactoryPostProcessor() {
        filterStrings = new HashSet<>();
    }

    public Set<String> getFilterStrings() {
        return filterStrings;
    }

    public void setFilterStrings(Set<String> filterStrings) {
        this.filterStrings.clear();
        for (String filterString : filterStrings) {
            this.filterStrings.add(filterString);
        }
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for(String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);

            StringValueResolver stringValueResolver = strVal -> {
                if(isFilter(strVal)) {
                    return "********";
                }
                return strVal;
            };

            BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(stringValueResolver);
            visitor.visitBeanDefinition(beanDefinition);
        }

    }

    private boolean isFilter(Object strVal) {
        String s = strVal.toString();
        return this.filterStrings.contains(s);
    }
}
