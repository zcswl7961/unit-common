package com.zcswl.mybatis.controller;

import com.zcswl.mybatis.common.api.PageResult;
import com.zcswl.mybatis.common.params.StudentQuery;
import com.zcswl.mybatis.entity.Student;
import com.zcswl.mybatis.serivce.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoucg
 * @date 2021-01-11 14:38
 */
@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {


    private final StudentService studentService;

    @GetMapping
    public String test() {
        studentService.test1();
        return "111";
    }

    @GetMapping("/hello")
    public String test1() {
        studentService.testMybatisMapper();
        return "12";
    }


    @PostMapping("/page")
    public PageResult<Student> pageQuery(@RequestBody StudentQuery query) {
        return studentService.pageQuery(query);
    }



}
