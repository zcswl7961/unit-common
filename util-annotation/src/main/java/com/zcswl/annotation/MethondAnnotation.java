package com.zcswl.annotation;

import java.lang.annotation.*;

/**
 * Created by zhoucg on 2018-12-18.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethondAnnotation {


    String name() default "zhoucg";
    String url()  default "www.zhoucg.com";
}
