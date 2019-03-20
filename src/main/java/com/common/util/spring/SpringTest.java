package com.common.util.spring;

import com.common.util.spring.action.ActorServciceTest;
import com.common.util.spring.aop.UserDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.ClassPathResource;

import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by zhoucg on 2019-03-04.
 */
public class SpringTest {

    public static void main(String[] args) {
//        XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("beanFactoryFile.xml"));
//
//
//        /**
//         * spring beanFactory基类操作
//         */
//        User user = (User) beanFactory.getBean("user");
//        String name = user.getName();
//        System.out.println("userName=="+name);

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

    /**
     * spring 国际化操作
     */
    @Test
    public void i18ntest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beanFactoryFile.xml");
        Object[] params = {"Wgp", new GregorianCalendar().getTime()};

        String str1 = context.getMessage("test", params, Locale.US);
        String str2 = context.getMessage("test", params, Locale.CHINA);

        System.out.println(str1);
        System.out.println(str2);
    }

    /**
     * ApplicationEvent 和 ApplicationListener的使用
     * 当程序运行时，Spring会将发出ApplicationTestEvent时间转给我们自定义的ApplicationTestListener监听器进一步进行处理
     * 这个时典型的观察者的模式
     */
    @Test
    public void testApplicationListener() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beanFactoryFile.xml");
        ApplicationTestEvent applicationTestEvent = new ApplicationTestEvent("hello","msg");
        applicationContext.publishEvent(applicationTestEvent);
    }

    /**
     * Spring 类型转化器
     */
    @Test
    public void conversionServiceTest() {
        DefaultConversionService serivce = new DefaultConversionService();
        boolean actual = serivce.canConvert(String.class, Boolean.class);
        Assert.assertEquals(true, actual);
        Object acc = serivce.convert("true", Boolean.class);
        Assert.assertEquals(true, ((Boolean)acc).booleanValue());
    }


    /**
     * AOP面向 切面编程
     */
    @Test
    public void aopTest() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beanFactoryFile.xml");
        UserDao userDao = (UserDao) applicationContext.getBean("userDaos");
        userDao.addUser();
    }


    /**
     * Spring 事务支持
     */
    @Test
    public void aopTransactionTest() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beanFactoryFile-shiwu.xml");
        ActorServciceTest userDao = (ActorServciceTest) applicationContext.getBean("actorServciceTest");
        userDao.test();
    }


}
