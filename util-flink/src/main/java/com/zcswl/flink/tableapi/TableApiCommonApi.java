package com.zcswl.flink.tableapi;

import com.google.inject.internal.cglib.core.$DuplicatesPredicate;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.BatchTableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.catalog.GenericInMemoryCatalog;
import org.apache.flink.types.Row;

/**
 * 有一个地方：我的本地注册的flink-verison 是<flink.version>1.11.2</flink.version>
 * 但是貌似api的文档介绍你要看着：https://ci.apache.org/projects/flink/flink-docs-release-1.12/dev/table/common.html 貌似这个里面是符合的
 * 版本1.111 还是会存在已经废弃的api文档
 * update-mode:
 * https://nightlies.apache.org/flink/flink-docs-release-1.12/zh/dev/table/streaming/dynamic_tables.html#continuous-queries
 *
 * @author zhoucg
 * @date 2021-03-07 18:45
 */
public class TableApiCommonApi {

    public static void main(String[] args) throws Exception {

        // 1.创建环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 1.11 基于老版本的Table 的流处理和批处理环境
        // 基于老版本的流式处理
        /*EnvironmentSettings oldStreamSettings = EnvironmentSettings.newInstance()
                .useOldPlanner()
                .inStreamingMode()
                .build();
        StreamTableEnvironment oldStreamTableEnv = StreamTableEnvironment.create(env, oldStreamSettings);
        // 基于老版本的批处理
        ExecutionEnvironment batchEnv = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment oldBatchEnv = BatchTableEnvironment.create(batchEnv);*/



        // 1.12 基于blink的Table 的流处理和批处理环境
        // 基于blink的流处理 实际上1.11 默认使用StreamTableEnvironment.create(env); 就是blink的流处理
        // 创建表的运行环境 默认使用的是blink useBlinkPlanner
        StreamTableEnvironment tableEnvironment = StreamTableEnvironment.create(env);
        // 基于Blink的批处理
        EnvironmentSettings blinkBatchEnvSettings = EnvironmentSettings.newInstance()
                .inStreamingMode()
                .useBlinkPlanner()
                .build();
        TableEnvironment blinkBatchTableEnv = TableEnvironment.create(blinkBatchEnvSettings);


        // 创建表
        // connect 版本过期
        // https://blog.csdn.net/weixin_43943806/article/details/110371983
        // https://ci.apache.org/projects/flink/flink-docs-release-1.11/dev/table/connectors/
        String createTable = String.format(
                "CREATE TABLE UserScores (name STRING)\n" +
                        "WITH (\n" +
                        "  'connector' = 'kafka',\n" +
                        "  'topic' = 'zhoucg-wl',\n" +
                        "  'properties.bootstrap.servers' = '192.168.120.130:9092',\n" +
                        "  'properties.group.id' = 'testGroup',\n" +
                        "  'format' = 'json',\n" +
                        "  'scan.startup.mode' = 'group-offsets'\n" +
                        ")");
        String createTablePrint = String.format(
                "CREATE TABLE print_table (\n" +
                        " f0 INT,\n" +
                        " f1 INT,\n" +
                        " f2 STRING,\n" +
                        " f3 DOUBLE\n" +
                        ") WITH (\n" +
                        " 'connector' = 'print'\n" +
                        ")"
        );
        tableEnvironment.executeSql(createTablePrint);
        // tableEnvironment.executeSql(createTable);
        Table table = tableEnvironment.sqlQuery("SELECT * FROM print_table");


        tableEnvironment.registerCatalog("ceshi", new GenericInMemoryCatalog("ceshi"));


        DataStream<Row> infoDataStream1 = tableEnvironment.toAppendStream(table, Row.class);
        infoDataStream1.print();


        // Table & Sql Connector
        // https://ci.apache.org/projects/flink/flink-docs-release-1.11/dev/table/connectors/
        // Table Api and Sql Api 参考：
        // https://ci.apache.org/projects/flink/flink-docs-release-1.12/dev/table/common.html


        env.execute();


    }
}
