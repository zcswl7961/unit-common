package com.zcswl.mybatis.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author xingyi
 * @date 2021/6/24 8:16 下午
 */
@Data
@Table(name = "`t_student`")
public class Student {

    @Id
    @Column(name = "`id`")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`address`")
    private String address;
}
