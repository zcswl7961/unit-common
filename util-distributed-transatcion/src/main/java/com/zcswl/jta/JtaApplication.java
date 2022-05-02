package com.zcswl.jta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * jta:java Transaction APi
 * jta 规范是xa规范的java版本
 * @author zhoucg
 * @date 2021-01-15 13:41
 */
@SpringBootApplication
public class JtaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JtaApplication.class, args);
    }
}
