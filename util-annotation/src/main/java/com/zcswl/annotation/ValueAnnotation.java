package com.zcswl.annotation;

import java.lang.annotation.*;

/**
 * Created by zhoucg on 2018-12-18.
 * 成员变量注解
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueAnnotation {

    String value() default "success";

}
