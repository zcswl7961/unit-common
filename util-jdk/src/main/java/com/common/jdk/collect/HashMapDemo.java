package com.common.jdk.collect;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HashMap ConcurrentHashMap
 *
 * @author zhoucg
 * @date 2020-12-17 21:16
 */
public class HashMapDemo {


    static final int MAXIMUM_CAPACITY = 1 << 30;


    private static int RESIZE_STAMP_BITS = 16;


    public static void main(String[] args) {
        HashMapDemo hashMapDemo = new HashMapDemo();
        int i = hashMapDemo.hashCode();
        int hash = hash(hashMapDemo);
        System.out.println(i);
        System.out.println(hash);


        int i1 = 1 ^ 2;
        int i2 = 1 >>> 2;
        System.out.println(i2);
        System.out.println(i1);


        int i3 = (16 - 1) & 120;
        int i4 = 120 % 16;
        System.out.println(i3);
        System.out.println(i4);


        // 测试对应的replaceAll 方法
        Map<String, String> maps = new HashMap<>(16);
        String zhoucg = maps.put("1", "zhoucg");
        System.out.println(zhoucg);

        maps.put("2","wl");
        String xxx = maps.put("1", "XXX");
        System.out.println(xxx);




        maps.replaceAll((k, v) -> {
            if (k.equals("2")) {
                return "ZHOUCG+WL";
            }
            return k;
        });

        // 如果指定的key不存在，则通过当前的key计算出来的value，插入到对应的map中，返回新的value值
        maps.computeIfAbsent("3", k -> k+":结果");
        // 如果指定的key存在，则返回当前map中key对应的value的值
        maps.computeIfAbsent("2", k -> k+":结果" );


        //maps.put("4", null);
        //maps.put(null, "null Value");


        maps.put(null,"ZHOUCGTEST");
        String s = maps.get(null);
        System.out.println(s);


        System.out.println(maps);


        Integer[] arr = new Integer[0];
        Integer[] array = new Integer[10];
        array[9] = 8;


        System.out.println(arr.length + ":::" + array.length);

        int i5 = tableSizeFor(11);
        int i6 = tableSizeFor(12);
        // 1
        int i55 = tableSizeFor(1);
        // 4
        int i66 = tableSizeFor(3);
        System.out.println(i55+ "::" + i66);

        System.out.println(i5+ ":" + i6);


        Map<String, String> mapss = new HashMap(7,0.75f);
        mapss.put("zhoucg","wl");
        mapss.size();


        int HASH_BITS = 0x7fffffff;
        System.out.println(Integer.MAX_VALUE);

        int hashx = hash(new HashMapDemo());
        int x = hashx & Integer.MAX_VALUE;

        System.out.println(hashx);
        System.out.println(x);


        Set<Map.Entry<String, String>> entries = mapss.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            // entry.getKey();
            // entry.getValue();
        }


        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>(7);
        concurrentHashMap.put("1","2");



        // 扩容的时候，会根据当前table的容量生成一个唯一的扩容标志
        // 00000000000000001000000000000000 | 0000000000000000000000000000000010000
        // 00000000000000001000000000010000
        int i7 = resizeStamp(16);

        // 如果对应的扩容标志小于 < 0 标识有线程正在参与扩容
        // 获取到锁的线程，即初始化的操作的时候，这个线程才具有初始化 n*2 倍的线程
        // 100000000001000000000000000000010 -
        // 然后判断标志位： 100000000001 （对应相同n实际上就是相等的）
        //
        int ixx = (i7 << 16) + 2;


        int i8 = Integer.numberOfLeadingZeros(16);
        int i9 = (1 << (RESIZE_STAMP_BITS - 1));


        System.out.println(i9);


        int xx = ((i7 << 16) + 2);
        int zz = xx >>>16;

        int i10 = i7 << 16;
        System.out.println(i7);
        System.out.println(i10);


        int i11 = (1 << (32 - RESIZE_STAMP_BITS)) - 1;
        System.out.println(i11);
    }

    static final int hash(Object key) {
        int h;
        //
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * 返回大于等于输入参数且最近的2的整数次幂的数，比如10，则返回16。该算法源码如下：
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    /**
     * ConcurrentHashMap
     */
    static final int resizeStamp(int n) {
        // Integer.numberOfLeadingZeros表示一个数从高位算起为0的位的个数，比如当n = 1时，结果为31，当n = 2时，结果为30。
        // 1 << (RESIZE_STAMP_BITS - 1)，由于RESIZE_STAMP_BITS = 16，所以这个就是把1的二进制左移15位，也就是2^16，2的16次方。
        // 上面两个结果做或运算，就相当于两个数向加，因为第二数的低16位全是0。假设n = 16，最后的结果为：32795
        // 由于每次传入的n不相同，所以每次结果也不同，也就是每次的标识也不同，这个值这么做的好处就是只在低16位有值，在下面计算sizeCtl的时候，只要继续左移16位，那低16位也就没有值了
        return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
    }

}
