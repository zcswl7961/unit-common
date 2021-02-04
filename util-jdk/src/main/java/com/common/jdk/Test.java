package com.common.jdk;

import java.util.BitSet;
import java.util.Random;

/**
 * @author zhoucg
 * @date 2021-01-19 15:10
 */
public class Test {


    public static void main(String[] args) {

        Long x = Long.MAX_VALUE >> 1;
        int z = 6 >>> 1;
        System.out.println(z);

        System.out.println(x);

        int running = (-1 << 29) | 0;
        System.out.println(running);

        int SHUTDOWN = 3 << 29;
        System.out.println(SHUTDOWN);
        int STOP = 2 << 29;

        System.out.println(STOP);


        int i = ctlOf(3 << 29, 0);
        System.out.println("sdafasdf:"+i);

        String s = Integer.toBinaryString(i);
        String s1 = Integer.toBinaryString(-1);
        System.out.println(s);
        System.out.println(s1);

        int totoa = (1 << 29) - 1;
        String s3 = Integer.toBinaryString(totoa);


        BitSet bitSet = randomBitSet(100, 70, new Random());
        System.out.println(bitSet);
        boolean b = bitSet.get(11);
        boolean b1 = bitSet.get(9);
        System.out.println("b=="+ b + " b1=="+b1);

    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    /**
     * 该代码来源于 org.springframework.cloud.sleuth.sampler.ProbabilityBasedSampler
     *
     *  这个也是称之为水库采样算法：
     *
     *
     */
    static BitSet randomBitSet(int size, int cardinality, Random rnd) {
        BitSet result = new BitSet(size);
        int[] chosen = new int[cardinality];
        int i;
        for (i = 0; i < cardinality; ++i) {
            chosen[i] = i;
            result.set(i);
        }
        for (; i < size; ++i) {
            int j = rnd.nextInt(i + 1);
            if (j < cardinality) {
                result.clear(chosen[j]);
                result.set(i);
                chosen[j] = i;
            }
        }
        return result;
    }

}
