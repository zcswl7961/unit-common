package com.mapstruct.demo;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author zhoucg
 * @date 2020-09-16 21:57
 */
public class MapStructExample {


    public static void main(String[] args) {

        CarPo carPo = CarPo.builder()
                .brand("BMW")
                .id(1)
                .carName("宝马")
                .carWord("BAOMA")
                .createTime(new Date())
                .build();

        CarVo carVo = CarCovertBasic.CAR_COVERT_BASIC.convertVo(carPo);

        System.out.println(carVo);

        // =================== 集合类型转换
        List<CarPo> lists = Collections.singletonList(carPo);
        List<CarVo> carVoList = CarCovertBasic.CAR_COVERT_BASIC.converts(lists);

        // =================== 多个映射
        AttributePo attributePo = AttributePo.builder()
                .price(1.2)
                .color("RED")
                .build();

        CarVo doubleSingle = CarCovertBasic.CAR_COVERT_BASIC.convertBo(carPo, attributePo);

    }
}
