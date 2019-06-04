package com.common.util.controller;

import com.zcg.configuration.HelloConfiguration;
import com.zcg.service.StarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhoucg on 2018-09-29.
 */
@RestController
@RequestMapping("/service/api/v1")
public class RestControllerImpl {

    @Autowired
    private StarterService starterService;

    @Autowired
    private HelloConfiguration helloConfiguration;

    @GetMapping("/get")
    public String get(@RequestParam("apikey") String apikey){
        System.out.println(apikey);
        String[] splitArray = starterService.split(",");
        helloConfiguration.print();
        return "get";
    }
}
