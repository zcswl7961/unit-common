package com.zcswl.leecode;

/**
 * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 *
 * https://leetcode-cn.com/problems/kth-largest-element-in-an-array/
 *
 * 输入: [3,2,1,5,6,4] 和 k = 2
 * 1 2 3 4 5 6
 * 输出: 5
 * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 * 1 2 2 3 3 4 5 5 6
 * 输出: 4
 * @author zhoucg
 * @date 2021-04-07 15:37
 */
public class 未排序数组找到第k个最大元素 {

    /**
     * 排序算法，
     */
    public int findKthLargest(int[] nums, int k) {
        return 1;
    }


    /**
     * 冒泡排序
     * 插入排序
     * 选择排序
     */
    public void sort(int[] nums) {
        // 插入排序
        for (int i = 1; i < nums.length ; i++) {
            for (int j = i; j > 0;j--) {
                if (nums[j] < nums[j-1]) {
                    int tmp = nums[j-1];
                    nums[j-1] = nums[j];
                    nums[j] = tmp;
                } else{
                    break;
                }
            }
        }

        // 冒泡排序
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length - i - 1;j++) {
                if (nums[j] > nums[j+1]) {
                    int tmp = nums[j+1];
                    nums[j+1] = nums[j];
                    nums[j] = tmp;
                }
            }
        }
        // 选择排序，以指定的字段为基准
        for (int i = 0; i< nums.length ;i++) {
            int sortIndex = i;
            for (int j = sortIndex+1; j< nums.length ; j++) {
                if (nums[i] > nums[j]) {
                    sortIndex = j;
                }
                //交换对应的 sortIndex 和i的值
                int tmp = nums[i];
                nums[i] = nums[sortIndex];
                nums[sortIndex] = tmp;
            }
        }
    }
}
