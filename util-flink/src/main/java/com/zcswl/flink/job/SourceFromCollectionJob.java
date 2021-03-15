package com.zcswl.flink.job;

import com.zcswl.flink.entity.Student;
import org.apache.commons.compress.utils.Lists;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.List;

/**
 * @author zhoucg
 * @date 2021-02-28 12:05
 */
public class SourceFromCollectionJob {

    public static void main(String[] args) throws Exception {

        // Environment
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Source from
        List<Student> lists = Lists.newArrayList();
        lists.add(new Student("ZJCJDX","zhoucg",100));
        lists.add(new Student("HNMYJJXY","wl",12));
        lists.add(new Student("ZJCJDX","yhh",13));
        lists.add(new Student("HNMYJJXY","ljj",13));
        lists.add(new Student("HNMYJJXY","qcc",14));
        lists.add(new Student("ZJCJDX","zdd",15));

        DataStream<Student> singleDataStreamSource = env.fromCollection(lists);
        // env.fromSource()

        // ==========算子操作========================
        // keyBy 的操作
        // 聚合的算子必须要基于keyBy进行计算，也就是说， sum,max,maxBy,reduce 都是基于KeyedStream 流进行计算
        // map, flatMap 都是可以基于DataStream进行计算
        KeyedStream<Student, String> singleStringKeyedStream = singleDataStreamSource.keyBy(Student::getSchool);

        // sum 算子 每来一个流，进行一次sum操作，增量聚合操作
        DataStream<Student> count = singleStringKeyedStream.sum("count");
        // max 算子 max的算子结果只是更新了当前对象中的count的值，其他的值不会更新
        DataStream<Student> count1 = singleStringKeyedStream.max("count");
        // maxBy 算子
        DataStream<Student> maxByCount = singleStringKeyedStream.maxBy("count");
        // reduce算子操作
        singleStringKeyedStream.reduce((t1,t2) -> {
            // t1, 之前聚合的结果
            // t2, 当前最新的状态
            return new Student(t1.getSchool(), t2.getName(), Math.max(t1.getCount(), t2.getCount()));
        });
        // split()， select()
        // Connect 和 CoMap
        // union DataStream # union

        // Sink
        count.print();

        // 执行
        env.execute();

    }
}
