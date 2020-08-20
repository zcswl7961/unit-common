package com.zcswl.annotation;

import java.lang.annotation.*;

/**
 * 基于Spring Aop 对Controller 接口参数进行规则判定
 *
 * @author zhoucg
 * @date 2019-11-07 9:51
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamFilter {

    /**
     * 用于判断实体类的字节码对象, POJO
     */
    Class<?> clazz();

    /**
     * 需要检测的实体类参数对象
     * @return
     */
    String[] param();
}
