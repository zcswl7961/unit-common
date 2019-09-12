package com.common.util.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * Arrays.sort(int[] a)源码解析
 * @author: zhoucg
 * @date: 2019-08-20
 */
public class ArraysExample {

    public static void main(String[] args) {

        int[] arrSort = new int[287];
        for(int i=0;i<287;i++) {
            int randomInt = new Random().nextInt(100);
            arrSort[i] = randomInt;
        }
        Arrays.sort(arrSort);





    }
}
