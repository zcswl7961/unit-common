package com.zcswl.mybatis;

import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author xingyi
 * @date 2024/8/16
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor pageInterceptor() {
        PaginationInterceptor pageInterceptor = new PaginationInterceptor();
        Properties properties = new Properties();
        //允许在运行时根据多数据源自动识别对应方言的分页
        properties.setProperty("autoRuntimeDialect", "TRUE");
        pageInterceptor.setProperties(properties);
        pageInterceptor.setDialectType("oracle");
        return pageInterceptor;
    }

    @Bean
    public IKeyGenerator oracleKeyGenerator() {
        return new OracleKeyGenerator();
    }
}
