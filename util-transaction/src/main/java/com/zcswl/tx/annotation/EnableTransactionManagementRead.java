package com.zcswl.tx.annotation;

import com.zcswl.tx.support.DataSourceAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 基于aop的方式实现数据库的主备切换
 * @author zhoucg
 * @date 2019-12-30 14:41
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({DataSourceAspect.class})
public @interface EnableTransactionManagementRead {
}
