package com.zcswl.mybatis.controller;

import com.zcswl.mybatis.entity.Author;
import com.zcswl.mybatis.mapper.AuthorMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhoucg
 * @date 2020-11-15 15:27
 */
@RestController
@RequestMapping("/mapper")
public class MapperController {

    private final AuthorMapper authorMapper;

    public MapperController(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }


    @GetMapping
    public String mybatisTest() {
        Author author = authorMapper.selectAuthor(500);
        return author.toString();
    }
}
