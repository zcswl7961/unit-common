package com.zcswl.leecode;

/**
 * 动态规划
 * @author zhoucg
 * @date 2021-04-22 9:01
 */
public class 爬楼梯 {

    // f(n) = f(n-1) + f(n-2)
    public int climbStairs(int n) {
        int p = 0; int q = 0; int r = 1;
        for (int i = 1; i<= n; i++) {
            p = q;
            q = r; // f(n-1)
            r = p + q;
            // f(1) = f(0) + f(-1);
            // f(2) = f(1) + f(0);
        }
        return r;
    }
}
