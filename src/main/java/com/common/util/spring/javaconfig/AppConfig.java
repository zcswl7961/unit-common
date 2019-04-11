package com.common.util.spring.javaconfig;

import com.common.util.spring.People;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhoucg on 2019-04-09.
 * spring 3.* 使用javaConfig配置
 * 它允许开发者将bean定义和在Spring配置XML文件到Java类中
 */
@Configuration
public class AppConfig {


    @Bean(name = "javaPeople")
    public People people() {
        People people = new People();
        people.setName("javaConfig");
        return people;
    }
}
