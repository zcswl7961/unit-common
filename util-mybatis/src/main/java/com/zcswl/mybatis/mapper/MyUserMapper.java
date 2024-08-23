package com.zcswl.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zcswl.mybatis.entity.MyUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyUserMapper extends BaseMapper<MyUser> {

    Integer insertInner(MyUser myUser);
}