package com.common.guava;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Created by zhoucg on 2019-01-30.
 * 我们知道，Map是一种键值对映射，这个映射是键到值得映射，而BiMap首先也是一个Map,
 * 他的特别之处在于，既提供键到值的映射，也提供值到键的映射，所以它是双向Map
 * 想象这么一个场景，我们需要做一个星期几的中英文表示的相互映射，例如Monday对应的中文表示是星期一，
 * 同样星期一对应的英文表示是Monday。这是一个绝好的使用BiMap的场景。
 */
public class BiMapDemo {


    public static void main(String[] args) {
        BiMap<String,String> weekNameMap = HashBiMap.create();

        weekNameMap.put("星期一","Monday");
        weekNameMap.put("星期二","Tuesday");
        weekNameMap.put("星期三","Wednesday");
        weekNameMap.put("星期四","Thursday");
        weekNameMap.put("星期五","Friday");
        weekNameMap.put("星期六","Saturday");
        weekNameMap.put("星期日","Sunday");
        System.out.println("星期日的英文名是" + weekNameMap.get("星期日"));
        System.out.println("Sunday的中文是" + weekNameMap.inverse().get("Sunday"));
    }
}
