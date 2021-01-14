package com.zcswl.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * code - value 映射
 * @author zhoucg
 * @date 2021-01-06 10:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Select<K, V> implements Serializable {

    private K code;

    private V value;
}
