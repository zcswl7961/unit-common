package com.mapstruct.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucg
 * @date 2020-09-16 21:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarVo {

    private Integer id;
    private String brand;
    private String carAnotherName;
    private String catAnotherWord;

    /**
     * 映射 CarPo -> createTime
     */
    private String createAt;


    /**
     * =============AttributePo
     */
    private String colorStr;
    private double price;
}
