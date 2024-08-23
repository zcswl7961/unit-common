package com.zcswl.user.invoke;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * 判断当前是否需要进行日志采集
 * @author xingyi
 * @date 2023/6/19
 */
@Service
public class TraceContext {

    @Bean
    @ConditionalOnProperty(value = "trace.log.enable",havingValue = "true")
    public BaseTraceAnnotationInterceptor baseTraceAnnotationInterceptor() {
        // 设置对应的其他参数
        return new BaseTraceAnnotationInterceptor();
    }

}
