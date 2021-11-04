package com.zcswl.mybatis.serivce;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zcswl.mybatis.common.api.PageResult;
import com.zcswl.mybatis.common.params.StudentQuery;
import com.zcswl.mybatis.entity.Student;
import com.zcswl.mybatis.mapper.StudentMapper;
import lombok.AllArgsConstructor;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    public PageResult<Student> pageQuery(StudentQuery query){
        PageHelper.startPage(query.getCurrentPage(), query.getPageSize(), buildOrderBy(query, "id"));
        List<Student> students = studentMapper.selectAllList(query);
        return new PageResult<>(((Page<Student>) students).getTotal(), students);

    }


    public <T extends StudentQuery> String buildOrderBy(T condition, String defaultSortBy) {
        String sortBy, sortType;
        if (null == condition) {
            if (null == defaultSortBy) {
                return null;
            }
            sortBy = defaultSortBy;
            sortType = "DESC";
        } else {
            sortBy = StrUtil.isBlank(condition.getSortBy()) ? defaultSortBy : condition.getSortBy();
            if (null == sortBy) {
                return null;
            }
            sortType = StrUtil.isBlank(condition.getSortType()) ? "DESC" : condition.getSortType();
        }
        return StrUtil.format("`{}` {}", sortBy, sortType);
    }




}
