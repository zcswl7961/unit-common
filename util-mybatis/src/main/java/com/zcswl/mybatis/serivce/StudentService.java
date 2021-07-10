package com.zcswl.mybatis.serivce;

import com.zcswl.mybatis.entity.Student;
import com.zcswl.mybatis.mapper.StudentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xingyi
 * @date 2021/6/24 8:22 下午
 */
@Service
@AllArgsConstructor
public class StudentService {

    private final StudentMapper studentMapper;

    /**
     * 开启事物操作
     */
    @Transactional(rollbackFor = Exception.class)
    public void test1() {
        Student student = new Student();
        student.setName("zjhoucg");
        student.setAddress("asdfasdf");

        studentMapper.insert(student);
        test2();

    }


    @Transactional(rollbackFor = Exception.class)
    public void test2() {
        int x = 1/0;
        studentMapper.update("1212", 2L);
        System.out.println(12);
    }


}
