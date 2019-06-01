package com.common.util.guava;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author: zhoucg
 * @date: 2019-05-31
 */
public class GoogleCollectionUtils {


    public static void main(String[] args) {
        /**
         * Google guava Maps使用
         */
        Map<String,String> map = Maps.newHashMap();
        map.put("1","A");
        map.put("2","B");
        Set<String> sets = Sets.newHashSet();
        sets.add("3");
        sets.add("4");
        Map<String,String> returnFunction = Maps.asMap(sets,a -> String.valueOf(a.hashCode()));
        System.out.println(returnFunction);


        Map<String,String> filterMaps = Maps.filterKeys(map,a -> a.equals("1"));
        System.out.println("filterMaps过滤的map信息（符合条件）："+filterMaps);


        Map<String,String> mapV2 = Maps.newHashMap();
        mapV2.put("2","B");
        mapV2.put("3","C");
        MapDifference<String,String> differenceMap = Maps.difference(map,mapV2);
        System.out.println(differenceMap);

        Map<String,String> fiterValues = Maps.filterValues(map,a -> a.equals("1"));
        System.out.println(fiterValues);


        /**
         * 这个和Spring框架中的Assert相似
         */
        Preconditions.checkArgument(1==1,"错误的数据表达式，进行显示");
        Boolean flag = Objects.equals(null,null);
        System.out.println(flag);

    }


    /**
     * ImmutableList
     */
    @Test
    public void testImmutableList() {
        ImmutableList<String> of = ImmutableList.of("1","2","3","4");
        System.out.println(of);

        /**
         * 不可变集合
         */
        ImmutableMap<String,String> ofMap = ImmutableMap.of("key1","value1","key2","key2");
        System.out.println(ofMap);

    }


    @Test
    public void testOptional() {

        Optional<String> possible = Optional.fromNullable(null);
        if(possible.isPresent()) {
            System.out.println(true);
        }

    }


}
