package com.common.jdk.collect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: zhoucg
 * @date: 2019-10-18
 */
public class ArrayListExample {

    public static void main(String[] args) {
        List<String> list = new ArrayList();

        list.add("A");
    }


    /**
     * 正确的删除List数据
     */
    public static void removeList() {

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");

        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()) {
            String item = iterator.next();
            if(item.equals("1")) {
                iterator.remove();
            }
        }


    }


}
