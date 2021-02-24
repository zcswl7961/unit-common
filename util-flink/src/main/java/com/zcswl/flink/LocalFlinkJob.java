package com.zcswl.flink;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author zhoucg
 * @date 2021-02-23 17:11
 */
public class LocalFlinkJob {

    private static int index = 1;


    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

        DataStream<String> fileStream = env.readTextFile("G:\\学习文档\\字节码编译.txt");

        DataStream<String> map = fileStream.map(s -> (index++) + "输出得结果" + s);

        //4.打印输出sink
        map.print();
        //5.开始执行
        env.execute();


    }
}
