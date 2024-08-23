package com.zcswl.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.zcswl.mybatis.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {

    @Value("${spring.sql.init.platform:mysql}")
    private String platform;



    /*@Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        // 设置别名包扫描路径
        sessionFactory.setTypeAliasesPackage("com.zzk.demo.entity"); // 替换为你的模型类所在的包名
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setUseGeneratedKeys(true);
        sessionFactory.setConfiguration(configuration);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 1, mybatis映射的 xml文件维护尽量进行统一操作
        // 2，需要兼容mybatis-mapper的相关内容
        Resource[] resources = resolver.getResources("classpath:mapper/*.xml");
        List<Resource> finalMappers = Lists.newArrayList(resources);
        switch (platform) {
            case DataSourceConstant.DM_ORACLE:
                //finalMappers.addAll(Lists.newArrayList(resolver.getResources("classpath:dmOracleMapper/*.xml")));
                break;
            case DataSourceConstant.DM_MYSQL:
                //finalMappers.addAll(Lists.newArrayList(resolver.getResources("classpath:dmMysqlMapper/*.xml")));
                break;
            default:
                break;
        }
        sessionFactory.setMapperLocations(finalMappers.toArray(new Resource[0]));
        return sessionFactory.getObject();
    }
*/

}
