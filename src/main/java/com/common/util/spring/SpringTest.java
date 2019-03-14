package com.common.util.spring;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by zhoucg on 2019-03-04.
 */
public class SpringTest {

    public static void main(String[] args) {
        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("beanFactoryFile.xml"));


        /**
         * spring beanFactory基类操作
         */
        User user = (User) beanFactory.getBean("user");
        String name = user.getName();
        System.out.println("userName=="+name);

        /**
         * spring框架扩展
         */
        ApplicationContext ap = new ClassPathXmlApplicationContext("beanFactoryFile.xml");
        User appUser = (User)ap.getBean("user");
        System.out.println("appUser.getName()=="+appUser.getName());


    }

    /**
     * PropertyPlaceholderConfigurer特性类的使用，特殊的BeanFactoryPostProcessor
     */
    @Test
    public void PropertyPlaceholderConfigurertest() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beanFactoryFile.xml");
        HelloMessage helloMessage = (HelloMessage) applicationContext.getBean("hellomessage");
        System.out.println(helloMessage);
    }

    /**
     * 自定义BeanFactoryPostProcessor
     */
    @Test
    public void myBeanFactoryPostProcessortest() {
        ConfigurableListableBeanFactory bf = new XmlBeanFactory(new ClassPathResource("selfBeanFactoryPostProcessor-Test.xml"));
        BeanFactoryPostProcessor bfpp = (BeanFactoryPostProcessor) bf.getBean("myBeanFactoryPostProcessor");
        bfpp.postProcessBeanFactory(bf);
        User user = (User) bf.getBean("user");
        System.out.println(user);
        //或者使用ApplicationContext，自定注入
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beanFactoryFile.xml");
        User user1 = (User) applicationContext.getBean("user");
        System.out.println(user1);
    }
}
