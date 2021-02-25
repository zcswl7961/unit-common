package com.zcswl.flink.processfunction;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * FlatMap  Tuple 二元组
 *
 * @author zhoucg
 * @date 2021-02-24 18:00
 */
public class FlatMapFunctionDemo implements FlatMapFunction<String, Tuple2<String, Integer>> {
    @Override
    public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {

    }
}
