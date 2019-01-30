package com.common.util.guava;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Map;
import java.util.Set;

/**
 * Created by zhoucg on 2019-01-30.
 * guava类库 Table 双键的Map数据
 * Table是Guava提供的一个接口 Interface Table<R,C,V>，由rowKey+columnKey+value组成
 * 它有两个键，一个值，和一个n行三列的数据表类似，n行取决于Table对对象中存储了多少个数据
 * 主要的方法：com.google.common.collect.Table
 *
 */
public class TableExample {


    public static void main(String[] args) {


        Table<String,String,Integer> tables = HashBasedTable.create();

        tables.put("a","javase",80);
        tables.put("b","javasee",80);
        tables.put("c","javases",80);
        tables.put("d","javases",80);
        tables.put("e","javasee",80);

        /**
         * 获取Table的值的结果
         */
        Set<Table.Cell<String,String,Integer>> cells = tables.cellSet();
        for(Table.Cell<String,String,Integer> temp : cells) {
            String rowKey = temp.getRowKey();
            String columnKey = temp.getColumnKey();
            Integer value = temp.getValue();
            System.out.println("rowKey:" + rowKey + " columnKey:" + columnKey + " value:" + value);
        }

        /**
         * 是否包含指定的row数据
         */
        tables.containsRow("a");

        /**
         * 是否包含指定的column数据
         */
        tables.containsColumn("javase");

        /**
         * 根据colume归并，抽取对应的row 和 value map
         */
        Map<String,Integer> rowValueMap = tables.column("javasee");
        System.out.println(rowValueMap);

        /**
         * 根据row归并，抽取对应的row（key）和 value (value) map
         */
        Map<String,Integer> columnValueMap =tables.row("a");
        System.out.println(columnValueMap);






    }
}
