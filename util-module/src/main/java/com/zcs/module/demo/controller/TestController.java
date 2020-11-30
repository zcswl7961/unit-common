package com.zcs.module.demo.controller;

import cn.hutool.core.date.DateUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

/**
 * only aliyun test
 * @author zhoucg
 * @date 2020-11-30 15:00
 */
@RestController
public class TestController {


    @GetMapping("/test")
    public String test() {
        Random random = new Random();
        int i = random.nextInt(100000000);
        return "ZHOUCG：current time: " + DateUtil.formatDateTime(new Date()) + "  current random:" + i;
    }



}
