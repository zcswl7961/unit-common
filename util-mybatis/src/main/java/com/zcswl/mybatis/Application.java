package com.zcswl.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 关于mybatis原生使用@Mapper和@MapperScan的关系
 * 在使用Mybatis持久层框架来操作数据库时，我们可以使用@Mapper注解和@MapperScan注解来将Mapper接口类交给Sprinig进行管理。
 * 方式一：使用@Mapper注解
 *      优点：粒度更细
 *      缺点：直接在Mapper接口类中加@Mapper注解，需要在每一个mapper接口类中都需要添加@Mapper注解，较为繁琐
 * 方式二：使用@MapperScan注解
 *      通过@MapperScan可以指定要扫描的Mapper接口类的包路径
 *
 * Mapper PageHelper 学习文档：https://blog.csdn.net/zcswl7961/article/details/109582909
 * @author zhoucg
 * @date 2018-12-27 17:22
 */
@SpringBootApplication
@MapperScan(basePackages = "com.zcswl.mybatis.mapper")
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
