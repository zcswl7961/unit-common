package com.zcswl.jta.controller;

import com.zcswl.jta.serivce.TestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoucg
 * @date 2021-01-15 14:35
 */
@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {

    private final TestService testService;


    @GetMapping
    public String test() {
        testService.test();
        return "SUCCESS";
    }
}
