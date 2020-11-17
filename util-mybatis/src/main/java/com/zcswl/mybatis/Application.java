package com.zcswl.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
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
