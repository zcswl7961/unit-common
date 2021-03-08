package com.zcswl.flink.tableapi;

import com.zcswl.flink.watermarks.StationLog;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;


/**
 * @author zhoucg
 * @date 2021-03-07 18:14
 */
public class TableExample {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> dataStreamSource = env.socketTextStream("192.168.129.128", 8888);

        DataStream<StationLog> windowWindowedStream = dataStreamSource
                // 流元素扁平化处理，转换成StationLog
                .flatMap(new FlatMapFunction<String, StationLog>() {
                    private static final long serialVersionUID = 9027908143422405274L;

                    @Override
                    public void flatMap(String value, Collector<StationLog> out){
                        String[] words = value.split(",");
                        out.collect(new StationLog(words[0], words[1], words[2], Long.parseLong(words[3]), Long.parseLong(words[4])));

                    }
                    // 设置对应的flatMap 算子的并行度为4
                });

        // 创建表环境
        StreamTableEnvironment tableEnvironment = StreamTableEnvironment.create(env);

        // 基于流创建表
        Table dataTable = tableEnvironment.fromDataStream(dataStreamSource);

        // 调用Table Api 进行转换操作
        // 可以看到官方推荐我们使用：Table select(Expression... fields) 这种方式
        Table resultTable = dataTable.select("from, to")
                .where("stationId = 'station1'");

        // 执行sql
        tableEnvironment.createTemporaryView("station", dataTable);
        String sql = "select to from station where stationId = 'station1'";
        Table resultSqlTable = tableEnvironment.sqlQuery(sql);


        // 转换成流
        tableEnvironment.toAppendStream(resultTable, Row.class).print();
        tableEnvironment.toAppendStream(resultSqlTable, Row.class).print();

        env.execute();

    }
}
