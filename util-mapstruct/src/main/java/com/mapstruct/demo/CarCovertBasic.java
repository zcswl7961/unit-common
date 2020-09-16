package com.mapstruct.demo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 使用 map struct进行转换
 *     FIXME 对于复杂的字段映射，注解过于费劲
 *
 * @author zhoucg
 * @date 2020-09-16 21:55
 */
@Mapper
public interface CarCovertBasic {

    CarCovertBasic CAR_COVERT_BASIC = Mappers.getMapper(CarCovertBasic.class);

    @Mappings({
            @Mapping(source = "carName", target = "carAnotherName"),
            @Mapping(source = "carWord", target = "catAnotherWord"),
            @Mapping(source = "createTime", target = "createAt", dateFormat = "YYYY-MM-dd HH:mm:ss") // 同时，这个也可以使用表达式来写： @Mapping(source = "createTime", target = "createAt", expression = "java(com.date.util.DateUtil.dateToStr(carPo.getCreateTime()))")
    })
    CarVo convertVo(CarPo carPo);

    /**
     * 通过converts内部会自动调用this.convertVo()进行转换
     */
    List<CarVo> converts(List<CarPo> carPos);


    /**
     * TODO 这个如何先映射 convertVo接口
     */
    @Mappings({
            @Mapping(source = "source2.color", target = "colorStr")
    })
    CarVo convertBo(CarPo source1, AttributePo source2);
}
