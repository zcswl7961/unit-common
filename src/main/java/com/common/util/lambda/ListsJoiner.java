package com.common.util.lambda;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author zhoucg
 * @date 2018-11-29
 *
 *
 * java8 lambda 新特性使用
 */
public class ListsJoiner<K,T> {

    /**
     * 内部的连接工具
     * @param <A>
     */
    private class ListJoiner<A> {
        List<A> list;
        Function<A, K> keyfunc;
        String name;

        public ListJoiner(List<A> list, Function<A, K> keyfunc, String name) {
            this.list = list;
            this.name = name;
            this.keyfunc = keyfunc;
        }
    }

    protected ListsJoiner(RowCreator<T> rowCreater, RowUpdater<T> rowUpdater) {
        this.rowCreater = rowCreater;
        this.rowUpdater = rowUpdater;
    }

    public static <K, T> ListsJoiner<K, T> create(RowCreator<T> rowFactory, RowUpdater<T> rowUpdater) {
        return new ListsJoiner<>(rowFactory, rowUpdater);
    }

    private RowCreator<T> rowCreater;
    private RowUpdater<T> rowUpdater;

    public interface RowCreator<T> {
        T create();
    }

    public interface RowUpdater<T> {
        T update(T t, String name, Object obj);
    }

    private List<ListJoiner> joiners = Lists.newArrayList();

    public <A> ListsJoiner<K, T> add(List<A> list, Function<A, K> keyfunc, String name) {
        joiners.add(new ListJoiner(list, keyfunc, name));
        return this;
    }

    public List<T> join() {
        List<T> result = Lists.newArrayList();
        Map<K, T> baseJoinerMap = Maps.newHashMap();
        for (int i = 0; i < joiners.size(); i++) {
            ListJoiner lj = joiners.get(i);
            lj.list.stream().forEach(item -> {
                K key = (K) lj.keyfunc.apply(item);
                T row = baseJoinerMap.get(key);
                if (row == null) {
                    row = rowCreater.create();
                    baseJoinerMap.put(key, row);
                    result.add(row);
                }
                rowUpdater.update(row, lj.name, item);
            });
        }
        return result;
    }





}
