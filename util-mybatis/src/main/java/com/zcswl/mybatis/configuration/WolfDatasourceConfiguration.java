//package com.zcswl.mybatis.configuration;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * 从库配置
// * @author zhoucg
// * @date 2018-12-27 17:22
// */
//@Configuration
//@MapperScan(basePackages = "com.zcswl.mybatis.mapper.wolf",sqlSessionFactoryRef = "wolfSqlSessionFactory")
//public class WolfDatasourceConfiguration {
//
//    @Bean(name = "wolfDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.wolf")
//    public DataSource setDatasource() {
//        return new DruidDataSource();
//    }
//
//    @Bean(name = "wolfTransactionManager")
//    public DataSourceTransactionManager setTransactionManager(@Qualifier("wolfDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//
//    @Bean(name = "salveSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory setSqlSessionFactory(@Qualifier("wolfDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/wolf/*.xml"));
//        return bean.getObject();
//    }
//
//    @Bean(name = "wolfSqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("salveSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//}
