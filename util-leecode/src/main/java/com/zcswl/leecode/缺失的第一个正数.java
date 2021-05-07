package com.zcswl.leecode;

/**
 *
 * leecode: https://leetcode-cn.com/problems/first-missing-positive/solution/que-shi-de-di-yi-ge-zheng-shu-by-leetcode-solution/
 *  实际上，对于一个长度为 N 的数组，其中没有出现的最小正整数只能在 [1, N+1] 中
 *
 * @author zhoucg
 * @date 2021-05-07 13:44
 */
public class 缺失的第一个正数 {

    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] <= 0) {
                nums[i] = n+1;
            }
        }

        for (int i = 0; i < n; ++i) {
            int num = Math.abs(nums[i]);
            if (num <= n) {
                nums[num - 1] = -Math.abs(nums[num - 1]);
            }
        }
        for (int i = 0; i < n; ++i) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }
        return n + 1;
    }
}
