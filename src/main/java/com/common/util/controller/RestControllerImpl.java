package com.common.util.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhoucg on 2018-09-29.
 */
@RestController
@RequestMapping("/service/api/v1")
public class RestControllerImpl {

    @RequestMapping("/get")
    public String get(){
        return "get";
    }
}
