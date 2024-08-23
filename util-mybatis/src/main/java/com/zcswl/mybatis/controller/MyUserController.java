package com.zcswl.mybatis.controller;

import com.zcswl.mybatis.entity.MyUser;
import com.zcswl.mybatis.serivce.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class MyUserController {

    @Autowired
    private MyUserService myUserService;

    @GetMapping("/add")
    public String add(){

        myUserService.insert();
        return "success";
    }

    @GetMapping("/get")
    public MyUser getById(Integer userId){
        return myUserService.getById(userId);
    }

}
