package com.zcswl.mybatis.common.params;

import lombok.Data;

/**
 * @author xingyi
 * @date 2021/11/4
 */
@Data
public class StudentQuery {


    private Integer currentPage;

    private Integer pageSize;

    private String sortBy;

    private String sortType;
}
