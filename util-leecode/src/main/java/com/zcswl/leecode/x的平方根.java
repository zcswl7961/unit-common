package com.zcswl.leecode;

import java.math.BigDecimal;

/**
 *
 * https://leetcode-cn.com/problems/sqrtx/solution/x-de-ping-fang-gen-by-leetcode-solution/
 *
 * 变形操作 基于数的二分查找，注意变形要求小数点精确到xxx
 *
 * https://blog.csdn.net/xiao_jj_jj/article/details/106018702
 *
 * 袖珍计算器算法  （这个方法有点扯）
 * @author zhoucg
 * @date 2021-04-16 16:55
 */
public class x的平方根 {

    public static void main(String[] args) {
        int x = 7;
        System.out.println(mySqrt1(x));
    }


    /**
     * 二分查找
     * x2 <= x
     * 二分查找的精髓：https://blog.csdn.net/xiao_jj_jj/article/details/106018702
     *
     */
    public static int mySqrt(int x) {
        // 7
        int l = 0, r = x, ans = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2; // 4
            // mid = 2
            if ((long) mid * mid <= x) {
                ans = mid;// 2
                // 这个为啥 l = mid+1
                // 因为 二分查找啊
                //l++;
                l = mid +1; // 3 + 2
            } else {
                //r--;
                r = mid-1; // 3
            }
        }
        return ans;
    }

    /**
     * 牛顿迭代，牛批的算法
     * 切线方程的原理
     * (x0 + a/xo) / 2  越来越逼近对应的值的结果
     *
     */
    public static int mySqrt1(int x) {
        if (x == 0) {
            return 0;
        }
        double c = x, x0 = x;
        while (true) {
            double xi = 0.5 * (x0 + x/x0);
            // 误差的范围
            if (Math.abs(x0 - xi) < 1e-7) {
                break;
            }
            x0 = xi;
        }
        BigDecimal b = new BigDecimal(x0);
        double d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(d);
        return (int)x0;
        // 精确到多少位得计算操作
    }


}
