package com.zcswl.mybatis.mapper;

import com.zcswl.mybatis.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author xingyi
 * @date 2021/6/24 8:16 下午
 */
@Repository
public interface StudentMapper {

    void insert(@Param("param")Student student);

    void update(@Param("address") String address, @Param("id") Long id);
}
