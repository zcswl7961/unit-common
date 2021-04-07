package com.zcswl.leecode;

import java.util.Arrays;

/**
 * 远亲不如近邻操作
 * 问题核心是，输入两个数组
 * [1,2,3,10,7] ,居民的位置
 * [4,5] , 搬家的方案
 * 输出，每一个方案对应居民中最近的位置
 * @author zhoucg
 * @date 2021-04-07 10:23
 */
public class Solution1 {


    public static int[] solve(int n, int m, int[] a, int[] b) {
        // n 居民个数。 m 方案个数 int[] a 居民的位置 int[] b 方法的位置
        // 首先是把具体的位置进行排序
        Arrays.sort(a);
        int[] res =new int[m];
        for (int i=0 ; i < m; i++) {
            res[i] = getRes(a, b[i]);
        }
        return res;
    }

    private static int getRes(int[] a, int target) {
        // 居民位置 int[] a  target：目标位置
        // 1 2 6 8 10
        // 4   3   6
        //       2
        int nearest = Math.min(Math.abs(a[0] - target), Math.abs(a[a.length - 1]- target));
        // 二分查找法
        int l = 0 ,r = a.length - 1;
        int mid = (l + r) / 2;
        nearest = Math.min(nearest, Math.abs(a[mid] - target));
        while (l < r) {
            if (a[mid] > target) {
                r = mid -1;
            } else if (a[mid] < target) {
                l = mid + 1;
            } else {
                return 0;
            }
            mid = (l + r) / 2;
            nearest = Math.min(nearest, Math.abs(a[mid] - target));
        }
        nearest = Math.min(nearest, Math.abs(a[l]- target));
        return nearest;
    }
}
