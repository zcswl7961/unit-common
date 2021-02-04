package com.common.jdk.jvm;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**软引用：（Soft Reference）
 *
 * 对应的测试参数：-Xms5m -Xmx5m -XX:+PrintGCDetails -XX:-UseAdaptiveSizePolicy -XX:SurvivorRatio=8
 *
 * -Xmx：表示最大堆内存
 * -Xms：表示最小堆内存
 *
 * -Xmn：表示新生代内存大小
 *
 * 堆：内存=e + s0 + ParOldGen
 * 新生代（PSYoungGen）   + 老年代（ParOldGen）
 *
 * PSYoungGen： Eden区    总内存= e + 1个so
 *              s0(from)
 *              s1(to)
 * ParOldGen:   老年代
 *
 * -XXSurvivorRatio=3：代表Eden:Survivor = 3    根据Generation-Collection算法(目前大部分JVM采用的算法)，
 * 一般根据对象的生存周期将堆内存分为若干不同的区域，一般情况将新生代分为Eden ，两块Survivor；
 * 计算Survivor大小， Eden:Survivor = 3，总大小为5120,3x+x+x=5120  x=1024
 *
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
            System.out.println(i+"对应的值"+obj);
        }
        System.out.println(Runtime.getRuntime().maxMemory()>>20);
        System.out.println(Runtime.getRuntime().totalMemory()>>20);
    }
}
