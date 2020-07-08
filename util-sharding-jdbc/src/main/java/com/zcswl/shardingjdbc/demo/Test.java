package com.zcswl.shardingjdbc.demo;

import java.sql.SQLException;

/**
 * @author zhoucg
 * @date 2020-06-14 11:06
 */
public class Test {



    public static void main(String[] args) throws SQLException {

        // 分库分表测试
        // 分库分表解决主键自增问题
        ShardingJdbcDataSource shardingJdbcDataSource = new ShardingJdbcDataSource();

        DataRepository dataRepository = new DataRepository(shardingJdbcDataSource.getShardingDataSource());

        dataRepository.demo();

        // 读写分离测试
        /*MasterSlaveDataSource masterSlaveDataSource = new MasterSlaveDataSource();
        DataRepository dataRepository = new DataRepository(masterSlaveDataSource.getDataSource());

        dataRepository.demoWriterAndRead();*/
    }
}
