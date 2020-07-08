package com.zcswl.shardingjdbc.demo;

import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * 读写分离的数据库涉及
 *
 * @author zhoucg
 * @date 2020-06-14 13:57
 */
public class MasterSlaveDataSource {



    public DataSource getDataSource() throws SQLException {

        MasterSlaveRuleConfiguration masterSlaveRuleConfiguration = new MasterSlaveRuleConfiguration("ds_master_slave", "ds_master", Arrays.asList("ds_slave0","ds_slave1"));

        return MasterSlaveDataSourceFactory.createDataSource(createDataSourceMap(), masterSlaveRuleConfiguration, new Properties());
    }


    private Map<String, DataSource> createDataSourceMap() {

        Map<String, DataSource> dataSourceMap = new HashMap<>();

        dataSourceMap.put("ds_master", DataSourceUtil.createDataSource("db1"));
        dataSourceMap.put("ds_slave0", DataSourceUtil.createDataSource("db2"));
        dataSourceMap.put("ds_slave1", DataSourceUtil.createDataSource("db3"));
        return dataSourceMap;

    }

}
