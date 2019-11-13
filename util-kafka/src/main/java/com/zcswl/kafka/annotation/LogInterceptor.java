package com.zcswl.kafka.annotation;

import java.lang.annotation.*;

/**
 * 在特殊行业中（气象），我们需要对一些特殊的消息di（常见叫法）进行处理，因此我们需要提供插件的方式（starter）
 * 注入特定的消息处理的PreHandler，并且，preHandler是需要按照一定的顺序进行执行
 *
 * <p>当一个插件注入PreHandler不存在对应的@LogInterceptor注解，我们默认为它的执行顺序是最后
 *
 * <p>当一个插件注入PreHandler存在对应的@LogInteceptor注解时，我们需要判定
 *
 * <p>该注解在一般意义上，提供私服公共包的形式
 * @author zhoucg
 * @date 2019-11-13 17:50
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogInterceptor {

    /**
     * 任务排序id
     * @return orderId
     */
    int order() default 0;
}
