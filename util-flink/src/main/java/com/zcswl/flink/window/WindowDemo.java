package com.zcswl.flink.window;

import org.apache.flink.streaming.api.datastream.AllWindowedStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;

/**
 * Flink window api
 * @author zhoucg
 * @date 2021-02-28 22:35
 */
public class WindowDemo {

    public static void main(String[] args) throws Exception {

        // 用于设置你的执行环境，任务执行环境，任务执行环境用于定义任务的属性、创建数据源以及最终启动任务的执行。
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        //
        DataStream<Integer> integerDataStreamSource = env.fromElements(1, 2, 3, 4, 5, 6, 7, 12, 11, 1, 2, 12, 14, 43, 1);

        // window 窗口操作
        // countWindowAll(int size);
        AllWindowedStream<Integer, GlobalWindow> allWindowedStream = integerDataStreamSource.countWindowAll(5);

        // 窗口最大数据
        SingleOutputStreamOperator<Integer> max = allWindowedStream.max(0);

        max.print();

        env.execute();
    }
}
