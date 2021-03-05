package com.zcswl.flink.stream;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.IterativeStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 迭代流
 * @author zhoucg
 * @date 2021-03-04 18:20
 */
public class IteratorStreamDemo {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStream<Long> someIntegers = env.generateSequence(0, 10);

        someIntegers.print();

        IterativeStream<Long> iterate = someIntegers.iterate();

        DataStream<Long> minusOne  = iterate.map((MapFunction<Long, Long>) value -> value - 1);

        minusOne.print("minusOne:");

        iterate.closeWith(minusOne);
//
//        DataStream<Long> stillGreaterThanZero = minusOne.filter((FilterFunction<Long>) value -> (value > 0));
//
//        stillGreaterThanZero.print("stillGreaterThanZero");
//
//
//        iterate.closeWith(stillGreaterThanZero);
//
//        DataStream<Long> lessThanZero = minusOne.filter((FilterFunction<Long>) value -> (value <= 0));
//
//        lessThanZero.print("lessThanZero:");

        env.execute();


    }
}
