package com.common.util.sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author: zhoucg
 * @date: 2019-08-30
 */
public class MaxIntComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer a, Integer b) {
        String strA = String.valueOf(a);
        String strB = String.valueOf(b);
        return Integer.parseInt(strA + strB) - Integer.parseInt(strB + strA);
    }

    public static String getMax(Integer[] intArray) {
        Arrays.sort(intArray, new MaxIntComparator());
        StringBuffer result = new StringBuffer();
        for (int i = intArray.length - 1; i >= 0; i--) {
            result.append(intArray[i]);
        }
        return result.toString();

    }

    public static void main(String[] args) {
        Integer[] ints = { 8, 87, 5, 53 };
        System.out.println(getMax(ints));
    }
}
