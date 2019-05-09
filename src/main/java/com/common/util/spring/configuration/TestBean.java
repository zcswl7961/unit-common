package com.common.util.spring.configuration;

import org.springframework.stereotype.Component;

/**
 * @author: zhoucg
 * @date: 2019-05-09
 */
@Component
public class TestBean {

    private String username;
    private String url;
    private String password;

    public void sayHello() {
        System.out.println("TestBean sayHello...");
    }

    public String toString() {
        return "username:" + this.username + ",url:" + this.url + ",password:" + this.password;
    }

    public void start() {
        System.out.println("TestBean 初始化。。。");
    }

    public void cleanUp() {
        System.out.println("TestBean 销毁。。。");
    }

}
