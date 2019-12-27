package com.zcswl.mybatis.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 主库配置
 * @author zhoucg
 * @date 2018-12-27 17:22
 */
@Configuration
@MapperScan(basePackages = "com.zcswl.mybatis.mapper.pmcis",sqlSessionFactoryRef = "pmcisSqlSessionFactory")
public class PmcisDatasourceConfiguration {

    @Bean(name = "pmcisDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.pmcis")
    public DataSource setDatasource() {
        return new DruidDataSource();
    }

    @Bean(name = "pmcisTransactionManager")
    public DataSourceTransactionManager setTransactionManager(@Qualifier("pmcisDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean(name = "pmcisSqlSessionFactory")
    @Primary
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("pmcisDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/pmcis/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "pmcisSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("pmcisSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
