package com.zcswl.mybatis.mapper;

import com.zcswl.mybatis.entity.Author;

/**
 * @author zhoucg
 * @date 2020-11-15 15:21
 */
public interface AuthorMapper {


    Author selectAuthor(int id);
}
