package com.zcswl.spring.plugin.annotation;

import java.lang.annotation.*;

/**
 *
 * @author zhoucg
 * @date 2020-03-12 16:04
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface LocalReference {

    /**
     * 默认是否追加操作
     * @return
     */
    boolean flag() default true;
}
