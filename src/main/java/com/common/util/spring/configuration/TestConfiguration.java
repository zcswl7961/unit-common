package com.common.util.spring.configuration;

import com.common.util.spring.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

/**
 * @author: zhoucg
 * @date: 2019-05-09
 */
@Configuration
@ComponentScan(basePackages  = "com.common.util.spring.configuration")
@ImportResource("classpath:beanFactoryFile-bast.xml")
@Import(AnotherConfiguration.class)
public class TestConfiguration {


    @Bean
    public User suser() {
        return new User();
    }

    public TestConfiguration() {
        System.out.println("TestConfiguration容器启动初始化中");
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfiguration.class);
        User user = (User)applicationContext.getBean("suser");
        TestBean testBean = (TestBean)applicationContext.getBean("testBean");
        System.out.println("testBean="+testBean);

        //Spring xml 文件信息配置的
        User xmlUser = (User) applicationContext.getBean("user");
        System.out.println(xmlUser.getName());

        //AnotherConfiguration配置信息中的
        User another = (User) applicationContext.getBean("anotherUser");
        System.out.println("anotherUser = "+another);
        System.out.println(user);
    }
}
