package com.zcswl.mybatis.mapper;

import com.zcswl.mybatis.entity.Author;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhoucg
 * @date 2020-11-15 15:21
 */
@Mapper
public interface AuthorMapper {


    Author selectAuthor(int id);
}
