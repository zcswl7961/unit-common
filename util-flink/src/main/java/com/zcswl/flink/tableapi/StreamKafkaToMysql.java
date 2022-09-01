package com.zcswl.flink.tableapi;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * 官方文档对应的kafka2mysql
 * @author xingyi
 * @date 2022/9/1
 */
public class StreamKafkaToMysql {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        StreamTableEnvironment tableEnvironment = StreamTableEnvironment.create(env);

        // 定义对应kafka的源表数据
        String source = String.format(
                "CREATE TABLE source (id int ,name STRING)\n" +
                        "WITH (\n" +
                        "  'connector' = 'kafka',\n" +
                        "  'topic' = 'zhoucgwl',\n" +
                        "  'properties.bootstrap.servers' = '172.16.100.109:9092',\n" +
                        "  'properties.group.id' = 'testGroup',\n" +
                        "  'format' = 'json',\n" +
                        "  'scan.startup.mode' = 'group-offsets'\n" +
                        ")");

        // 定义对应的mysql的结果表
        String target = "CREATE TABLE sink\n" +
                "(\n" +
                "    id          int,\n" +
                "    name        varchar\n" +
                ") WITH (\n" +
                "       -- 'connector' = 'stream-x'\n" +
                "\n" +
                "      'connector' = 'jdbc',\n" +
                "      'url' = 'jdbc:mysql://localhost:3306/test',\n" +
                "      'table-name' = 't_test',\n" +
                "      'username' = 'root',\n" +
                "      'password' = '123456',\n" +
                "\n" +
                "      'sink.buffer-flush.max-rows' = '1024', -- 批量写数据条数，默认：1024\n" +
                "      'sink.buffer-flush.interval' = '10000' -- 批量写时间间隔，默认：10000毫秒\n" +
                "      )";

        String transformSql = "insert into sink select * from source";

        TableResult tableResult = tableEnvironment.executeSql(source);
        TableResult tableResult1 = tableEnvironment.executeSql(target);
        TableResult tableResult2 = tableEnvironment.executeSql(transformSql);


        env.execute();
    }
}
