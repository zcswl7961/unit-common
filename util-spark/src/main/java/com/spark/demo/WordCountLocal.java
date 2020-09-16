package com.spark.demo;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

/**
 * 本地 Spark 任务调度
 * 1, 通过Project Structure -> modules -> Dependencies -> add jars 将spark 中的jars 加入到本地 ，开始本地调试模式
 *
 * 2, 会开启一个4040端口
 * @author zhoucg
 * @date 2020-09-16 17:18
 */
public class WordCountLocal {


    public static void main(String[] args) {

        // 开启本地spark 调度
        SparkConf sparkConf = new SparkConf().setAppName("WS").setMaster("local[*]");

        // 获取对应的SparkContext信息
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);

        // 获取对应的RDD 信息
        JavaRDD<String> rdd = javaSparkContext.textFile("G:\\04.txt");

        JavaPairRDD<String, Integer> counts = rdd
                //每一行都分割成单词，返回后组成一个大集合
                .flatMap(s -> Arrays.asList(s.split(" ")).iterator())
                //key是单词，value是1
                .mapToPair(word -> new Tuple2<>(word, 1))
                //基于key进行reduce，逻辑是将value累加
                .reduceByKey(Integer::sum);

        //先将key和value倒过来，再按照key排序
        JavaPairRDD<Integer, String> sorts = counts
                //key和value颠倒，生成新的map
                .mapToPair(tuple2 -> new Tuple2<>(tuple2._2(), tuple2._1()))
                //按照key倒排序
                .sortByKey(false);

        //取前10个
        List<Tuple2<Integer, String>> top10 = sorts.take(10);
        //打印出来
        for(Tuple2<Integer, String> tuple2 : top10){
            System.out.println(tuple2._2() + "  " + tuple2._1());
        }

        // 关闭
        javaSparkContext.close();
    }
}
