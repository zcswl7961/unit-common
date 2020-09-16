package com.mapstruct.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhoucg
 * @date 2020-09-16 21:53
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarPo {

    private Integer id;
    private String brand;
    private String carName;
    private String carWord;

    /**
     * 类型(名称)不一致的转换,
     */
    private Date createTime;

}
