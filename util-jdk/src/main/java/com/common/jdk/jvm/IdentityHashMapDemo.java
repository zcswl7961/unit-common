package com.common.jdk.jvm;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * IdentityHashMap类利用哈希表实现 Map 接口，比较键（和值）时使用引用相等性代替对象相等性。
 * @author zhoucg
 * @date 2021-02-07 13:38
 */
public class IdentityHashMapDemo {

    public static void main(String[] args) {


        Map<String, String> identityMap = new IdentityHashMap<>();


        identityMap.put(new String("1"), "12");
        identityMap.put(new String("1"),"121");

        // 这两个是同一个
        identityMap.put("3","3-1");
        identityMap.put("3","3-2");

        // 输出结果
        for (Map.Entry<String, String> entry : identityMap.entrySet()) {
            System.out.println("当前结果：" + entry.getKey() + ":" + entry.getValue());
        }


    }
}
