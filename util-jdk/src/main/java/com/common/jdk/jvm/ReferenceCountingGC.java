package com.common.jdk.jvm;

/**
 * @author zhoucg
 * @date 2020-10-09 16:37
 */
public class ReferenceCountingGC {

    public Object instance = null;

    /**
     * 1M = 1024 KB = 1024 * 1024 字节
     */
    private static final int INIT_1MB = 1024 * 1024;


    private final byte[] bigSize = new byte[2 * INIT_1MB];


    public static void testGC() {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;
        objA = null;
        objB = null;
        // 假设在这行发生GC，objA和objB是否能被回收？
        System.gc();
    }


    public static void main(String[] args) {
        testGC();
    }


}
