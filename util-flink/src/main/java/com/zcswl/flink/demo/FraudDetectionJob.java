package com.zcswl.flink.demo;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.core.execution.JobClient;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.walkthrough.common.sink.AlertSink;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;
import org.apache.flink.walkthrough.common.source.TransactionSource;

/**
 * Skeleton code for the datastream walkthrough
 * 定义了程序的数据流
 * @author zhoucg
 * @date 2021-02-20 17:07
 */
public class FraudDetectionJob {

    public static void main(String[] args) throws Exception {
        // 用于设置你的执行环境，任务执行环境，任务执行环境用于定义任务的属性、创建数据源以及最终启动任务的执行。
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        /*
        *  flink exection.runtime-mode
        */


        // flink运行环境的参数
        ExecutionConfig config = env.getConfig();

        /*
         * flink checkpoint
         */
        // 每 1000ms 开始一次 checkpoint
        env.enableCheckpointing(1000);
        // 高级选项：
        // 设置模式为精确一次 (这是默认值)
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        // 确认 checkpoints 之间的时间会进行 500 ms
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
        // Checkpoint 必须在一分钟内完成，否则就会被抛弃
        env.getCheckpointConfig().setCheckpointTimeout(60000);
        // 同一时间只允许一个 checkpoint 进行
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        // 开启在 job 中止后仍然保留的 externalized checkpoints
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);



        // 创建数据源操作
        // 数据源从外部系统例如 Apache Kafka、Rabbit MQ 或者 Apache Pulsar 接收数据
        DataStream<Transaction> transactions = env
                .addSource(new TransactionSource())
                .name("transactions");

        /**
         * process() 对流绑定了一个操作，这个操作将会对流上的每一个消息调用所定义好的函数
         */
        DataStream<Alert> alerts = transactions
                // 对同一账户的所有交易行为数据要被同一个并发的 task 进行处理。 使用keyBy() 对流进行分区
                .keyBy(Transaction::getAccountId)
                .process(new FraudDetector())
                .name("fraud-detector");
        // 聚合算子
        // 所有的聚合操作，都是基于分组操作之后进行的


        // sink 会将 DataStream 写出到外部系统，例如 Apache Kafka、Cassandra 或者 AWS Kinesis 等。
        // AlertSink 使用 INFO 的日志级别打印每一个 Alert 的数据记录，而不是将其写入持久存储，以便你可以方便地查看结果。
        alerts
                .addSink(new AlertSink())
                .name("send-alerts");

        // env.execute("Fraud Detection");
        JobClient jobClient = env.executeAsync("Fraud Detection");
        // 对于flink处理实时场景下，该防范会一直不会返回
        /*JobExecutionResult jobExecutionResult = jobClient.getJobExecutionResult().get();
        System.out.println("jobId====="+jobClient);*/
        System.out.println("jobClient==" + jobClient.toString());
        // 获取对应的JobClient 可以停止并保存对应的cp和sp

    }
}
