package com.zcswl.jta.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import javax.sql.XADataSource;

/**
 * @author zhoucg
 * @date 2021-01-15 13:51
 */
@Configuration
@MapperScan(basePackages = "com.zcswl.jta.mapper.db1",sqlSessionFactoryRef = "db1SqlSessionFactory")
public class Db1DatasourceConfiguration {

    /**
     * 获取XADataSource
     */
    @Bean(name = "db1DruidXADataSource")
    @ConfigurationProperties(prefix = "mysql.datasource.db1.druid")
    public XADataSource db1Datasource() {
        return new DruidXADataSource();
    }

    /**
     * AtomikosDataSourceBean
     */
    @Bean("atomikosDb1XaDatasource")
    public DataSource xaDataSource(@Qualifier("db1DruidXADataSource") XADataSource xaDataSource) {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(xaDataSource);
        atomikosDataSourceBean.setUniqueResourceName("db1DruidXADataSource");
        return atomikosDataSourceBean;
    }


    @Bean(name = "db1SqlSessionFactory")
    @Primary
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("atomikosDb1XaDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/db1/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "db1SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("db1SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
