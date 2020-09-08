package com.common.guava;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;

/**
 * ClassToInstanceMap
 *
 * ClassToInstanceMap是一种特殊的Map：它的键是类型，而值是符合键所指类型的对象。
 *
 * 这让ClassToInstanceMap包含的泛型声明有点令人困惑，但请记住B始终是Map所支持类型的上界——通常B就是Object。
 *
 * 对于ClassToInstanceMap，Guava提供了两种有用的实现：MutableClassToInstanceMap和 ImmutableClassToInstanceMap。
 * @author zhoucg
 * @date 2020-09-07 16:51
 */
public class ClassToInstanceMapDemo {


    public static void main(String[] args) {
        ClassToInstanceMap map = MutableClassToInstanceMap.create();

        map.putInstance(Integer.class, 1);
        map.putInstance(String.class, "1");


    }
}
