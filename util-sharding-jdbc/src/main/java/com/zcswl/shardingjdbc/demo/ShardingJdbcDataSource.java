package com.zcswl.shardingjdbc.demo;

import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author zhoucg
 * @date 2020-06-14 10:37
 */
public class ShardingJdbcDataSource {



    public DataSource getShardingDataSource() throws SQLException {

        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());


        shardingRuleConfig.getBindingTableGroups().add("t_order, t_order_item");
        shardingRuleConfig.getBroadcastTables().add("t_config");

        // 数据库分库策略 ，根据user_id 进行取模进行判断
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
        // 分表策略
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id", new ModuloShardingTableAlgorithm()));
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new Properties());
    }


    private Map<String, DataSource> createDataSourceMap() {

        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // 配置第一个数据源
        /*BasicDataSource dataSource1 =new BasicDataSource();
        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://127.0.0.1:3306/db1?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&remarks=true&useInformationSchema=true");
        dataSource1.setUsername("root");
        dataSource1.setPassword("123456");*/

        dataSourceMap.put("ds0", DataSourceUtil.createDataSource("db1"));

        // 配置第二个数据源
        /*BasicDataSource dataSource2 =new BasicDataSource();
        dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://127.0.0.1:3306/db2?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&remarks=true&useInformationSchema=true");
        dataSource2.setUsername("root");
        dataSource2.setPassword("123456");*/

        dataSourceMap.put("ds1", DataSourceUtil.createDataSource("db2"));

        return dataSourceMap;
    }


    private TableRuleConfiguration getOrderTableRuleConfiguration() {

        //TableRuleConfiguration result = new TableRuleConfiguration("t_order", "ds${0..1}.t_order${0..1}");
        TableRuleConfiguration result = new TableRuleConfiguration("t_order","ds${0..1}.t_order");
        result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
        return result;
    }

    // 雪花uuid
    private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
        KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("SNOWFLAKE", "order_id");
        return result;
    }


    private TableRuleConfiguration getOrderItemTableRuleConfiguration() {
        //TableRuleConfiguration result = new TableRuleConfiguration("t_order_item", "ds${0..1}.t_order_item${0..1}");
        TableRuleConfiguration result = new TableRuleConfiguration("t_order_item", "ds${0..1}.t_order_item");
        return result;
    }


}
