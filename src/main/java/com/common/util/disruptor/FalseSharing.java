package com.common.util.disruptor;

/**
 * @author: zhoucg
 * @date: 2019-06-25
 */
public class FalseSharing implements Runnable{

    public final static int NUM_THREADS = 4; // change
    public final static long ITERATIONS = 500L * 1000L * 1000L; //8个字节
    private final int arrayIndex;



    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];


    static
    {
        for (int i = 0; i < longs.length; i++)
        {
            longs[i] = new VolatileLong();
        }
    }

    public FalseSharing(final int arrayIndex)
    {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception
    {
        final long start = System.nanoTime();
        runTest();
        System.out.println("duration = " + (System.nanoTime() - start));
    }

    private static void runTest() throws InterruptedException
    {
        //创建4个线程
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new Thread(new FalseSharing(i));
        }

        for (Thread t : threads)
        {
            t.start();
        }

        for (Thread t : threads)
        {
            t.join();
        }
    }

    public void run()
    {
        long i = ITERATIONS + 1;
        while (0 != --i)
        {
            longs[arrayIndex].value = i;
        }
    }

    /**
     * 缓存行通常是64个字节
     * 对象本省的对象头要占4个字节，但是对象本身大小也要8字节的整数倍，
     *
     */
    public final static class VolatileLong
    {
        public volatile long value = 0L; //8个字节
        public long p1, p2, p3, p4, p5; // comment out
    }

}
