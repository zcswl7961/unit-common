package com.zcswl.mybatis.serivce;

import com.zcswl.mybatis.entity.Student;
import com.zcswl.mybatis.mapper.StudentMapper;
import lombok.AllArgsConstructor;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xingyi
 * @date 2021/6/24 8:22 下午
 */
@Service
@AllArgsConstructor
public class StudentService {

    private final StudentMapper studentMapper;


    public void testMybatisMapper() {
        Student student = studentMapper.selectByPrimaryKey(1L);
        System.out.println(student);
    }

    /**
     * 开启事物操作
     */
    @Transactional(rollbackFor = Exception.class)
    public void test1() {
        Student student = new Student();
        student.setName("zjhoucg");
        student.setAddress("asdfasdf");

        studentMapper.insert(student);
        // 事务生效
        // ((StudentService)AopContext.currentProxy()).test2();
        this.test2();
        // test2中的事务不会生效，使用对应的cglib也会生效
        // this.test2();
        System.out.print("first transaction message");

    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void test2() {
        studentMapper.update("1212", 2L);
        System.out.println(12);
    }


}
