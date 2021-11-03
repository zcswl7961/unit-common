package com.zcswl.mybatis.mapper;

import com.zcswl.mybatis.common.mybatis.BaseMapper;
import com.zcswl.mybatis.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author xingyi
 * @date 2021/6/24 8:16 下午
 */
@Repository
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 更新
     * @param address 地址
     * @param id id
     */
    void update(@Param("address") String address, @Param("id") Long id);
}
