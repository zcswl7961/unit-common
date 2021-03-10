package com.zcswl.flink.userbehavior;

import org.apache.commons.compress.utils.Lists;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.sql.Timestamp;
import java.util.List;

/**
 * 用户行为分析
 * 统计近1小时内的热门商品，每五分钟统计一次
 * @author zhoucg
 * @date 2021-03-09 18:53
 */
public class UserBehaviorCalculate {

    public static void main(String[] args) throws Exception {

        // Flink 的运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // 基于事件时间操作
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<String> dataStream = env.readTextFile("G:\\Data\\UserBehavior.csv");

        SingleOutputStreamOperator<ItemViewCount> aggregateStream = dataStream
                .map(s -> {
                    String[] split = s.split(",");
                    return new UserBehavior(Long.parseLong(split[0]), Long.parseLong(split[1]), Integer.parseInt(split[2]), split[3], Long.parseLong(split[4]));
                })
                .filter(data -> "pv".equals(data.getBehavior()))
                .assignTimestampsAndWatermarks(WatermarkStrategy.<UserBehavior>forMonotonousTimestamps()
                        .withTimestampAssigner((SerializableTimestampAssigner<UserBehavior>) (element, recordTimestamp) -> {
                            return element.getTimestamp() * 1000;
                        }))
                // 通过对用户行为的商品进行分组操作
                .keyBy(UserBehavior::getItemId)
                // 设置对应的
                .timeWindow(Time.hours(1), Time.minutes(5))
                // AggregateFunction<T, ACC, V> aggFunction,
                // ProcessWindowFunction<V, R, K, W> windowFunction
                .aggregate(new MyAggregateFunction(), new MyWindowFunction());

        // 收集统一窗口的所有商品的count数据，排序输出topn
        DataStream<String> resultStream = aggregateStream
                .keyBy(ItemViewCount::getWindowEnd)
                .process(new TopNHotItem(5));

        resultStream.print();


        env.execute();






    }


    private static class TopNHotItem extends KeyedProcessFunction<Long, ItemViewCount, String> {

        public int top;

        public TopNHotItem(int top) {
            this.top = top;
        }

        ListState<ItemViewCount> itemViewCountListState;

        @Override
        public void open(Configuration parameters) throws Exception {
            itemViewCountListState = getRuntimeContext().getListState(new ListStateDescriptor<ItemViewCount>("item-view-count-list", ItemViewCount.class));
        }

        private static final long serialVersionUID = -898854481969762822L;

        @Override
        public void processElement(ItemViewCount value, Context ctx, Collector<String> out) throws Exception {
            // 没来一条数据存入list中，并注册定时器
            itemViewCountListState.add(value);
            ctx.timerService().registerEventTimeTimer(value.getWindowEnd() + 1);
        }

        @Override
        public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {
            // 定时器触发，当前已经收到所有数据
            Iterable<ItemViewCount> itemViewCounts = itemViewCountListState.get();
            List<ItemViewCount> list = Lists.newArrayList(itemViewCounts.iterator());

            list.sort((o1, o2) -> o2.getCount().intValue() - o1.getCount().intValue());

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("=====================================");
            stringBuilder.append("窗口结束时间：").append(new Timestamp(timestamp - 1)).append("\n");

            for (int i = 0; i < Math.min(top, list.size()); i++) {
                ItemViewCount itemViewCount = list.get(i);
                stringBuilder.append("No ").append(i+1).append(":")
                        .append(" 商品ID= ").append(itemViewCount.getItemId())
                        .append(" 热门度= ").append(itemViewCount.getCount())
                        .append("\n");

            }
            stringBuilder.append("===================================\n\n");
            Thread.sleep(1000L);
            out.collect(stringBuilder.toString());


        }
    }

    private static class MyAggregateFunction  implements AggregateFunction<UserBehavior, ItemCount, ItemCount> {

        private static final long serialVersionUID = 4867344347741241975L;

        @Override
        public ItemCount createAccumulator() {
            ItemCount itemCount = new ItemCount();
            itemCount.setCount(0L);
            itemCount.setItemId(0L);
            return itemCount;
        }

        @Override
        public ItemCount add(UserBehavior value, ItemCount accumulator) {
            accumulator.setItemId(value.getItemId());
            accumulator.setCount(accumulator.getCount() + 1);
            return accumulator;
        }

        @Override
        public ItemCount getResult(ItemCount accumulator) {
            return accumulator;
        }

        @Override
        public ItemCount merge(ItemCount a, ItemCount b) {
            ItemCount itemCount = new ItemCount();
            itemCount.setItemId(a.getItemId());
            itemCount.setCount(b.getCount() + a.getCount());
            return null;
        }
    }

    private static class MyWindowFunction implements WindowFunction<ItemCount, ItemViewCount, Long, TimeWindow> {

        private static final long serialVersionUID = -8743840479318042147L;

        @Override
        public void apply(Long aLong, TimeWindow window, Iterable<ItemCount> input, Collector<ItemViewCount> out) throws Exception {
            long count = 0L;
            for (ItemCount itemCount : input) {
                count += itemCount.getCount();
            }
            ItemViewCount itemViewCount = new ItemViewCount(aLong,window.getEnd(), count);
            out.collect(itemViewCount);
        }
    }

    private static class ItemCount {

        /**
         * 当前所属商品id
         */
        private Long ItemId;

        /**
         * 当前数量总和
         */
        private Long count;

        public Long getItemId() {
            return ItemId;
        }

        public void setItemId(Long itemId) {
            ItemId = itemId;
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }
    }
}
