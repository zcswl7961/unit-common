package com.zcswl.flink.job;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author zhoucg
 * @date 2021-02-24 10:43
 */
public class WordCount {


    public static void main(String[] args) throws Exception {

        // args : --host 192.168.129.128 --port 8888 解析这种格式
        //ParameterTool parameterTool = ParameterTool.fromArgs(args);
        //String host = parameterTool.get("host");
        //int port = parameterTool.getInt("port");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


        //连接socket获取输入的数据
        DataStreamSource<String> text = env.socketTextStream("192.168.129.128", 8888, "\n");

        //计算数据
        DataStream<WordWithCount> windowCount = text.flatMap(new FlatMapFunction<String, WordWithCount>() {
            public void flatMap(String value, Collector<WordWithCount> out) throws Exception {
                String[] splits = value.split("\\s");
                for (String word:splits) {
                    out.collect(new WordWithCount(word,1L));
                }
            }
        })//打平操作，把每行的单词转为<word,count>类型的数据
                .keyBy(WordWithCount::getWord)//针对相同的word数据进行分组
                // .timeWindow(Time.seconds(2),Time.seconds(1))//指定计算数据的窗口大小和滑动窗口大小
                .sum("count");

        //把数据打印到控制台
        windowCount
                .addSink(new AlertSinkWorkCount())
                .name("send-alerts");
        windowCount.print()
                .setParallelism(1);//使用一个并行度
        //注意：因为flink是懒加载的，所以必须调用execute方法，上面的代码才会执行
        // 流处理的操作
        env.execute("streaming word count");
    }

    public static class WordWithCount{
        public String word;
        public long count;
        public WordWithCount(){}
        public WordWithCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "WordWithCount{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
