package com.common.jdk.jvm;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**软引用：（Soft Reference）
 *
 * @author zhoucg
 * @date 2020-10-09 17:22
 */
public class SoftReferenceTestGC {

    private static List<Object> list = new ArrayList<>();


    public static void main(String[] args) {
        testSoftReference();
    }

    private static void testSoftReference() {
        for (int i = 0; i < 10; i++) {
            byte[] buff = new byte[1024 * 1024];
            SoftReference<byte[]> sr = new SoftReference<>(buff);
            list.add(sr);
        }

        System.gc(); //主动通知垃圾回收

        for(int i=0; i < list.size(); i++){
            Object obj = ((SoftReference) list.get(i)).get();
            System.out.println(obj);
        }

    }
}
