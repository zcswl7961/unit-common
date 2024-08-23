package com.zcswl.user;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author xingyi
 * @date 2023/6/6
 */
public class MemoryLimited {

    private static Semaphore semaphore = new Semaphore(1024 * 1024, true);
    // acquire method to get a size length array
    public static byte[] createArray(int size) throws InterruptedException {
        // ask the semaphore for the amount of memory
        semaphore.acquire(size);
        // if we get here we got the requested memory reserved
        return new byte[size];
    }
    public static void releaseArray(byte[] array) {
        // we don't need the memory of array, release
        semaphore.release(array.length);
    }
    // allocation size, if N > 1M then there will be mutual exclusion
    static final int N = 600000;
    // the test program
    public static void main(String[] args) {
        // create 2 threaded executor for the demonstration
        ExecutorService exec = Executors.newFixedThreadPool(2);
        // what we want to run for allocation testion
        Runnable run = new Runnable() {
            @Override
            public void run() {
                Random rnd = new Random();
                // do it 10 times to be sure we get the desired effect
                for (int i = 0; i < 10; i++) {
                    try {
                        // sleep randomly to achieve thread interleaving
                        TimeUnit.MILLISECONDS.sleep(rnd.nextInt(100) * 10);
                        // ask for N bytes of memory
                        byte[] array = createArray(N);
                        // print current memory occupation log
                        System.out.printf("%s %d: %s (%d)%n",
                                Thread.currentThread().getName(),
                                System.currentTimeMillis(), array,
                                semaphore.availablePermits());
                        // wait some more for the next thread interleaving
                        TimeUnit.MILLISECONDS.sleep(rnd.nextInt(100) * 10);
                        // release memory, no longer needed
                        releaseArray(array);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        // run first task
        exec.submit(run);
        // run second task
        exec.submit(run);
        // let the executor exit when it has finished processing the runnables
        exec.shutdown();
    }
}
