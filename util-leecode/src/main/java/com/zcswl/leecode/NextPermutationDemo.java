package com.zcswl.leecode;

import java.util.Enumeration;

/**
 * leecode: 31. 下一个排列
 * 实现获取 下一个排列 的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
 * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
 * 必须 原地 修改，只允许使用额外常数空间。
 *
 * @author zhoucg
 * @date 2021-04-09 13:40
 */
public class NextPermutationDemo {

    public static void main(String[] args) {
        int[] nums = {2,3,1};
        nextPermutation(nums);
        for (int i = 0; i< nums.length ; i++) {
            System.out.println(nums[i]);
        }
    }

    // 1 2 3 4 -> 1243
    // 1 2 4 3 -> 1342 -> 1324
    // 1 2 5 4 3 -> 13542 13245
    // 1 2 1 5 3 -> 12351 -> 12315
    // 2 3 1
    // 思路，首先从后往前找，第一个出现降序的
    // 即 arr[i] > arr[i+1] }{
    private static void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        // 思路，从后往前找，依次是升序的，如果遇到降序的，返回对应的i
        while (i >= 0 && nums[i] >= nums[i+1]) {
            i--;
        }
        // 然后从后往前找，找到第一个后面有一个数大于前面的，然后替换
        // 然后找到i+1后面的数组反转操作
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[i] >= nums[j]) {
                j--;
            }
            swap(nums, i, j);
        }
        release(nums, i);

    }

    private static void swap(int[] nums, int j, int i) {
        int tmp = nums[j];
        nums[j] = nums[i];
        nums[i] = tmp;
    }

    private static void release(int[] nums, int j) {
        // 反转数组
        // 1 3 2 j=1
        int left = j+1;
        int right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }
}
