package com.zcswl.flink.state;

import com.zcswl.flink.demo.FraudDetectionJob;
import com.zcswl.flink.watermarks.StationLog;
import org.apache.flink.api.common.functions.*;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 *  https://zhuanlan.zhihu.com/p/104171679
 *
 *  Flink的一个算子有多个子任务，每个子任务分布在不同的实例上，我们可以把状态理解为某个算子子任务在其当前实例上的一个变量
 *  实际上，Flink的状态是由算子的子任务来创建和管理的。
 *
 *  Keyed State：
 *      每一个key对应一个状态
 *      横向扩展：状态随着key自动在多个算子子任务上迁移
 *  Operator State:
 *      一个算子子任务对应一个状态
 *      横向扩展：有多种状态重新分配的方式
 *      一个算子，并行度2
 *
 *
 * flink的状态管理
 * @see FraudDetectionJob
 * @author zhoucg
 * @date 2021-03-05 9:44
 */
public class FlinkStateDemo {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置状态后端 策略
        // env.setStateBackend()

        DataStreamSource<String> dataStreamSource = env.socketTextStream("127.0.0.1", 8888);

        SingleOutputStreamOperator<StationLog> windowWindowedStream = dataStreamSource
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
        //windowWindowedStream.keyBy(StationLog::getStationID)
        //        .map(new MyRichMapFunction()).print();




        // 基于keyedState 进行状态编码
        // 对于基站的信息：station1,18688822219,18684812319,10,1595158485855  基站id ，呼叫方，被叫放，呼叫持续时间，通话开始时间
        //              如果出现对于同一个基站的同一个呼叫人，两次呼叫的时间都大于10秒，我们认为该人是想朋友了
        SingleOutputStreamOperator<Tuple3<String, String, String>> outputStreamOperator = windowWindowedStream.keyBy((in) -> in.getStationID() + ":" + in.getFrom())
                .flatMap(new MyKeyedStateRichFlatMapFunction());

        // 简单一点，直接输出
        outputStreamOperator.print();


        env.execute();

    }

    // Tuple3 : to : 当前的呼叫人  t1:第一次的被呼叫的人  t2:第二次被呼叫的人
    private static class MyKeyedStateRichFlatMapFunction extends RichFlatMapFunction<StationLog, Tuple3<String, String, String>> {

        private ValueState<StationLog> valueState;


        @Override
        public void open(Configuration parameters) throws Exception {
            valueState = getRuntimeContext().getState(new ValueStateDescriptor<StationLog>("phone-station", StationLog.class));
        }





        @Override
        public void flatMap(StationLog value, Collector<Tuple3<String, String, String>> out) throws Exception {
            StationLog last = valueState.value();

            if (null != last) {
                if (last.getDuration() > 10) {
                    // 上一次的是大于10的，这个时候再判断本地是否大于10 的操作
                    if (value.getDuration() > 10) {
                        // 这个时候，实际上是满足的
                        out.collect(new Tuple3<>(value.getFrom(), last.getTo(), value.getTo()));
                    }
                }
            }
            // 存入，如果本地是大于10 的，才有必要进行存入
            if (value.getDuration() > 10) {
                valueState.update(value);
            }
        }

        @Override
        public void close() throws Exception {
            valueState.clear();
        }
    }

    // ValueState  keyedState
    private static class MyRichMapFunction extends RichMapFunction<StationLog, Integer> {

        private static final long serialVersionUID = 1710463325065419570L;
        private ValueState<Integer> keyCountState;


        @Override
        public void open(Configuration parameters) throws Exception {
            keyCountState = getRuntimeContext().getState(new ValueStateDescriptor<Integer>("key-count", Integer.class, 0));
        }

        @Override
        public Integer map(StationLog stationLog) throws Exception {
            Integer count = keyCountState.value();
            count++;
            keyCountState.update(count);
            return count;
        }
    }


    // Operator State
    // CheckPointeedFunction :@see https://segmentfault.com/a/1190000017262632
    private static class MyOperatorStateMapFunction implements MapFunction<StationLog, Integer> ,  CheckpointedFunction {

        // 定义一个本地变量，作为算子状态
        private Integer count = 0;

        @Override
        public Integer map(StationLog value) throws Exception {
            count ++;
            return count;
        }

        @Override
        public void snapshotState(FunctionSnapshotContext context) throws Exception {
        }

        @Override
        public void initializeState(FunctionInitializationContext context) throws Exception {
        }
    }
}