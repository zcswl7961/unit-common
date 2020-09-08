package com.zcswl.docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoucg
 * @date 2020-09-02 21:09
 */
@SpringBootApplication
@RestController
public class ApplicationDemo {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationDemo.class, args);
    }



    @GetMapping("/hello")
    public String hello() {
        return "Docker Image build success";
    }
}
