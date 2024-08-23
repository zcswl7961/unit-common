package com.zcswl.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "MY_USER")
@KeySequence("SEQ_MY_USER")
public class MyUser implements Serializable {

    @TableId(type = IdType.INPUT)
    private Long id;

    private String name;
}
