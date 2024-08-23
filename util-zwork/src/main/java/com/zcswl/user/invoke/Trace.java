package com.zcswl.user.invoke;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * a trace for invoke search current message
 *
 * @author xingyi
 * @date 2023/6/13
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Trace {

    /**
     * 当前trace对应的服务节点的数据
     */
    String operationName() default "";
}
