package com.zcswl.tx.annotation;

import java.lang.annotation.*;

/**
 * <p>该注解作用在方法上，基于{@code @EnableTransactionManagementRead} 的spring boot项目
 *    注解该注解类时，此方法或者此类对应的数据查询的事务管理会交给对应的read库(读库)中
 *
 * <p>TODO 基于接口和实现类上的注解实现，参考{@code Transactional},利用BeanPostProcessor动态增强对应的方法，切换对应的TransactionManager
 *
 * @author zhoucg
 * @date 2019-12-30 14:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ReadOnly {
}
