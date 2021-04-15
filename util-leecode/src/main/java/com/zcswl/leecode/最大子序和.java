package com.zcswl.leecode;

/**
 * 53. 最大子序和
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * @author zhoucg
 * @date 2021-04-14 11:16
 */
public class 最大子序和 {

    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        int i = maxSubArray(nums);
        System.out.println(i);
    }

    // 扎心了，这个题
    // 和股票计算的思路一致
    // @see 股票买卖
    public static int maxSubArray(int[] nums) {
        //nums = [-2,1,-3,4,-1,2,1,-5,4]
        int sum = nums[0];
        int ans = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (sum > 0) {
                sum += nums[i];
            } else {
                sum = nums[i];
            }
            ans = Math.max(ans, sum);

        }
        return ans;
    }
}
