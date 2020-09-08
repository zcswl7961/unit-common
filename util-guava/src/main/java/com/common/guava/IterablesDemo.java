package com.common.guava;

import com.google.common.collect.*;

import java.util.Iterator;
import java.util.List;

/**
 * @author zhoucg
 * @date 2020-09-07 17:02
 */
public class IterablesDemo {


    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();

        list.add("zhoucg");
        list.add("wl");

        Iterator<String> iterator = list.iterator();


    }
}
