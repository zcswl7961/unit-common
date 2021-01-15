package com.zcswl.jta.configuration;

import com.alibaba.druid.pool.xa.DruidXADataSource;
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
 * @date 2021-01-15 14:19
 */
@Configuration
@MapperScan(basePackages = "com.zcswl.jta.mapper.db2",sqlSessionFactoryRef = "db2SqlSessionFactory")
public class Db2DatasourceConfiguration {

    /**
     * 获取XADataSource
     */
    @Bean(name = "db2DruidXADataSource")
    @ConfigurationProperties(prefix = "mysql.datasource.db2.druid")
    public XADataSource db2Datasource() {
        return new DruidXADataSource();
    }

    /**
     * AtomikosDataSourceBean
     */
    @Bean("atomikosDb2XaDatasource")
    public DataSource xaDataSource(@Qualifier("db2DruidXADataSource") XADataSource xaDataSource) {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(xaDataSource);
        atomikosDataSourceBean.setUniqueResourceName("db2DruidXADataSource");
        return atomikosDataSourceBean;
    }


    @Bean(name = "db2SqlSessionFactory")
    @Primary
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("atomikosDb2XaDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/db2/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "db2SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("db2SqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
