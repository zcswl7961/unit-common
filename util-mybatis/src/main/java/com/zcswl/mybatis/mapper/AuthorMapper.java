package com.zcswl.mybatis.mapper;

import com.zcswl.mybatis.entity.Author;
import org.springframework.stereotype.Repository;

/**
 * @author zhoucg
 * @date 2020-11-15 15:21
 */
@Repository
public interface AuthorMapper {


    Author selectAuthor(int id);
}
