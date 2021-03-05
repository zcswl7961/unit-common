package com.zcswl.flink.stream;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * DataStream Transformations  v1.11
 * https://ci.apache.org/projects/flink/flink-docs-release-1.11/dev/stream/operators/
 *
 * @author zhoucg
 * @date 2021-03-04 14:24
 */
public class DataStreamTransformationsDemo {

    public static void main(String[] args) throws Exception {

        // Flink的流转换
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // DataStream -> DataStream
        //      map
        //      flatMap
        //      filter
        //      union
        //      join
        //          dataStream.join(otherStream)
        //              .where(<key selector>).equalTo(<key selector>)
        //              .window(TumblingEventTimeWindows.of(Time.seconds(3)))
        //              .apply (new JoinFunction () {...});
        // DataStream -> KeyedStream
        //      keyBy
        // KeyedStream -> DataStream
        //      reduce :“滚动”减少键控数据流。结合当前元素最后价值和降低排放的新值。(A "rolling" reduce on a keyed data stream.
        //                  Combines the current element with the last reduced value and emits the new value.)
        //      fold:  “滚动”褶皱与一个初始值的数据流。结合当前元素最后折叠价值和排放的新值。 @Deprecated
        //              A "rolling" fold on a keyed data stream with an initial value.
        //              Combines the current element with the last folded value and emits the new value.
        //      aggregations: 聚合
        //                     滚动聚合键控数据流。最小的区别和minBy分钟返回最小值,而minBy返回元素的最小值在这个领域(max和maxBy类似)。
        //              max maxBy min minBy
        // KeyedStream,KeyedStream -> DataStream
        //      join:
        //
        // KeyedStream -> WindowStream
        //      window:  dataStream.keyBy(value -> value.f0).window(TumblingEventTimeWindows.of(Time.seconds(5)));
        //      timeWindow:
        //      countWindow:
        // DataStream -> WindowedStream[AllWindowedStream]
        //      windowAll:dataStream.windowAll(TumblingEventTimeWindows.of(Time.seconds(5)));
        // WindowedStream(AllWindowedStream) -> DataStream
        //      apply: 引用 WindowedFunction
        // WindowedStream -> DataStream
        //      reduce():
        //              ReduceFunction
        //      fold:
        //          windowedStream.fold("start", new FoldFunction<Integer, String>() {
        //              public String fold(String current, Integer value) {
        //                  return current + "-" + value;
        //              }
        //          });
        //      Aggregations
        //          sum()
        //          min minBy max maxBy


        //DataStreamSource<Element> dataStream = env.fromElements(new Element("a", 1), new Element("a", 2), new Element("a", 3), new Element("a", 4), new Element("a", 5));
        //KeyedStream<Element, String> keyedStream = dataStream.keyBy(Element::getGroup);
        //DataStream<Element> reduce = keyedStream.reduce((ReduceFunction<Element>) (value1, value2) -> new Element(value1.getGroup(), value1.getCount() + value2.getCount()));

        //reduce.print();

        // The execute() method will wait for the job to finish and then return a JobExecutionResult,
        // this contains execution times and accumulator results.
        // this will execute with a long time for a stream
        JobExecutionResult execute = env.execute();

        // 异步调用，返回一个JobClient
        // JobClient jobClient = env.executeAsync();

    }


    private static class Element {
        private String group;
        private Integer count;

        public Element() {}

        public Element(String group, Integer count) {
            this.group = group;
            this.count = count;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "Element{" +
                    "group='" + group + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
