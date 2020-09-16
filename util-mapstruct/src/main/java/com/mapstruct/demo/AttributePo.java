package com.mapstruct.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhoucg
 * @date 2020-09-16 22:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributePo {

    private double price;
    private String color;

}
