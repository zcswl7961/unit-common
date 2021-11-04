package com.zcswl.mybatis.common.api;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

/**
 * @author xingyi
 * @date 2021/11/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    private Long total;

    private List<T> list;

    /**
     * 是否为空
     *
     * @param <T> 泛型参数
     * @return <T> 泛型参数
     */
    public static <T> PageResult<T> ofEmpty() {
        return new PageResult<>(0L, Lists.newArrayList());
    }

    /**
     * 返回分页结果
     *
     * @param list 需要分页的集合
     * @param <T>  泛型参数
     * @return <T> 泛型参数
     */
    public static <T> PageResult<T> ofPage(List<T> list) {
        if (CollUtil.isEmpty(list)) {
            return ofEmpty();
        }
        if (list instanceof Page) {
            return new PageResult<>(((Page<T>) list).getTotal(), list);
        } else {
            return new PageResult<>((long)list.size(), list);
        }
    }

    /**
     * 返回分页结果
     *
     * @param doList 需要分页的集合
     * @param voList 视图的集合
     * @param <T>    泛型参数
     * @return <T> 泛型参数
     */
    public static <T> PageResult<T> ofPage(List<?> doList, List<T> voList) {
        if (CollUtil.isEmpty(doList) || Objects.isNull(voList)) {
            return ofEmpty();
        }
        if (doList instanceof Page) {
            return new PageResult<>(((Page<?>) doList).getTotal(), voList);
        } else {
            return new PageResult<>((long)doList.size(), voList);
        }
    }

    /**
     * 返回分页结果
     *
     * @param total  总条数
     * @param voList 视图的集合
     * @param <T>    泛型参数
     * @return <T> 泛型参数
     */
    public static <T> PageResult<T> ofPage(Number total, List<T> voList) {
        if (null == voList) {
            return ofEmpty();
        }
        return new PageResult<>(total.longValue(), voList);
    }



}
