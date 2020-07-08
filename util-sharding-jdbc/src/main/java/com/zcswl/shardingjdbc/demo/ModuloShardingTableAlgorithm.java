package com.zcswl.shardingjdbc.demo;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author zhoucg
 * @date 2020-06-14 10:54
 */
public class ModuloShardingTableAlgorithm implements PreciseShardingAlgorithm<Long> {



    @Override
    public String doSharding(final Collection<String> tableNames, final PreciseShardingValue<Long> shardingValue) {
//        for (String each : tableNames) {
//            if (each.endsWith(shardingValue.getValue() % 2 + "")) {
//                return each;
//            }
//        }
//        throw new UnsupportedOperationException();
        Iterator<String> iterator = tableNames.iterator();
        return iterator.next();

    }

}
