package com.zcswl.leecode;

/**
 * 接雨水
 * https://leetcode-cn.com/problems/trapping-rain-water/solution/jie-yu-shui-by-leetcode/
 * @author zhoucg
 * @date 2021-04-14 17:23
 */
public class TrapDemo {

    public static void main(String[] args) {
        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        trap1(height);
    }


    /**
     * 直接按问题描述进行。对于数组中的每个元素，
     * 我们找出下雨后水能达到的最高位置，等于两边最大高度的较小值减去当前高度的值。
     * 暴力求解法
     */
    public static int trap(int[] height) {
        // [0,1,0,2,1,0,1,3,2,1,2,1]
        int ans = 0;
        int length = height.length;
        // 从第一个开始比较
        int leftMax = 0;
        int rightMax = 0;
        for (int i = 1;i < length -1;i++) {
            for (int j = i; j>= 0;j--) {
                leftMax = Math.max(leftMax, height[j]);
            }
            for (int j = i; j < length; j++) {
                rightMax = Math.max(rightMax, height[j]);
            }
            ans += Math.min(leftMax, rightMax) - height[i];
        }
        return ans;
    }

    /**
     * 动态规划的方法
     * 在暴力方法中，我们仅仅为了找到最大值每次都要向左和向右扫描一次。但是我们可以提前存储这个值。因此，可以通过动态编程解决。
     *
     * 1, 找到数组中从下标 i 到最左端最高的条形块高度 left_max。
     * 2, 找到数组中从下标 i 到最右端最高的条形块高度 right_max。
     * 3, 扫描数组 height 并更新答案
     *      累加 min(max_left[i],max_right[i]) - height[i]到 ans 上
     *
     *
     */
    public static int trap1(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int ans = 0;
        int size = height.length;
        int[] leftMax = new int[size];
        int[] rightMax = new int[size];
        leftMax[0] = height[0];
        for (int i = 1;i<size; i++) {
            //[0,1,0,2,1,0,1,3,2,1,2,1]
            // 变成了 0  1 1 2 2 2  3 3 2 2 2
            leftMax[i] = Math.max(height[i], leftMax[i - 1]);
        }
        rightMax[size - 1] = height[size-1];
        for (int j = size-2;j>=0;j--) {
            rightMax[j] = Math.max(height[j],rightMax[j+1]);
        }
        for (int i = 1; i < size - 1; i++) {
            // 左右两边的不用计算，会漏掉
            ans += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return ans;
    }


    /**
     * 双指针算法
     */
    public static int trap2(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int ans = 0;
        int left_max = 0, right_max = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= left_max) {
                    left_max = height[left];
                } else {
                    ans += (left_max - height[left]);
                }
                ++left;
            } else {
                // 双指针，右侧的数据大于左侧的数据
                if (height[right] >= right_max) {
                    right_max = height[right];
                } else {
                    ans += (right_max - height[right]);
                }
                --right;
            }
        }
        return ans;

    }
}
