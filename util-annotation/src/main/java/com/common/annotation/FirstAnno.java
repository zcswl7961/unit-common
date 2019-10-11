package com.common.annotation;

import java.lang.annotation.*;

/**
 * Created by zhoucg on 2018-12-18.
 * 水果名
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FirstAnno {

    String value() default "";

}
