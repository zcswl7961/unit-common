package com.zcswl.flink.demo;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author xingyi
 * @date 2022/8/25
 */
public class FlinkDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();


        

        executionEnvironment.execute();
    }
}
