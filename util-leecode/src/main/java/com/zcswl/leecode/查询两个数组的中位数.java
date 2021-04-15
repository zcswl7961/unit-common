package com.zcswl.leecode;

import javax.validation.constraints.Max;

/**
 * 使用二分查找查询两个有序增量数组的中位数
 * @author zhoucg
 * @date 2021-04-04 15:06
 */
public class 查询两个数组的中位数 {


    public double findMedianSortedArray(int[] s1, int[] s2) {
        // 1, 首先把数组长度小的放在前面，数组长度大的放在后面
        int m,n;
        if (s1.length > s2.length) {
            int[] tmp = s1;
            s1 = s2;
            s2 = tmp;
        }
        m = s1.length;
        n = s2.length;

        // 分割线左边的所有元素的个数需要满足 m + (n -m +1 ) /2
        int totalLeft = (m + n +1 ) / 2;

        int left = 0;
        int right = m;
        while (left < right) {
            // s1 上的分割线中的左边的最大值，要小于s2分割线右边的最小值
            // s2 上的分割线中的左边最大值，要小于s1分割线中的右边最小值
            int i = left + (right - left + 1) / 2;
            int j = totalLeft - i;
            if (s1[i-1] > s2[j]) {
                 // 下一轮搜索的区间[left, i - 1]
                right = i -1;
            } else {
                //  下一轮搜索的区间[i , right]
                left = i;
            }

        }

        int i = left;
        int j = totalLeft - i;
        int nums1LeftMax = i == 0 ? Integer.MIN_VALUE : s1[i-1];
        int nums1RightMin = i == m ? Integer.MAX_VALUE : s1[i];
        int nums2LeftMax = j == 0? Integer.MIN_VALUE  : s2[j-1];
        int nums2RightMin = j == 0 ? Integer.MAX_VALUE : s2[j];

        if ((m + n) % 2 == 1) {
            return Math.max(nums1LeftMax, nums2LeftMax);
        } else {
            return (double) (Math.max(nums1LeftMax,nums2LeftMax) + Math.min(nums1RightMin, nums2RightMin)) / 2;
        }
    }
}
