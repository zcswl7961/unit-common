package com.zcswl.leecode;

/**
 * @author zhoucg
 * @date 2021-04-15 17:29
 */
public class 合并两个有序数组 {

    public static void main(String[] args) {
        int[] nums1 = {1,2,3,0,0,0};
        int[] nums2 = {2,5,6};
        int m = 3 ;
        int n = 3;
        merge(nums1, m, nums2, n);
        for (int i = 0 ; i < nums1.length ;i ++) {
            System.out.println(nums1[i]);
        }

    }

    // 思路1
    // 复制原来的数组num1将m前个进行复制
    // 然后遍历操作,
    // 后再在进行mIndex 和nIndex 的判断
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        // 合并两个有序数组，将nums2数组中元素合并到nums1中
        if (n == 0) {
            return;
        }
        // 将对应得nums1得数组扩容操作
        // 复制一份的操作
        int[] numsCopyt = new int[m];
        for (int i = 0; i < m; i++) {
            numsCopyt[i] = nums1[i];
        }
        int mIndex = 0;
        int nIndex= 0;
        int pointer = 0;
        while(mIndex < m && nIndex < n) {
            if(numsCopyt[mIndex] < nums2[nIndex]) {
                // 左边的大于右边得，然后
                nums1[pointer] = numsCopyt[mIndex];
                mIndex++;
            } else {
                nums1[pointer] = nums2[nIndex];
                nIndex++;
            }
            pointer++;
        }
        // 拼接后面的操作
        if (mIndex == m) {
            // 凭借对应的n的值
            for (int i = pointer; i < m + n ; i++) {
                nums1[i] = nums2[nIndex];
                nIndex++;
            }
        } else if (nIndex == n) {
            for (int i = pointer; i< m + n; i++) {
                nums1[i] = numsCopyt[mIndex];
                mIndex++;
            }
        }
    }

    // 双指针的方法,其实和我写的方法有点类似
    public static void merge1(int[] nums1, int m, int[] nums2, int n) {
        int p1 = 0, p2 = 0;
        int[] sorted = new int[m+n];
        int cur;
        while (p1 < m || p2 < n) {
            if (p1 == m) {
                // 卧槽，它这个写的很妙啊，和我的思路差不多
                cur = nums2[p2++];
            } else if (p2 == n) {
                cur = nums1[p1++];
            } else if (nums1[p1] < nums2[p2]) {
                cur = nums1[p1++];
            } else {
                cur = nums2[p2++];
            }
            // 这个写的真好，我自己还搞了一个pointer。。。。。
            sorted[p1 + p2 - 1] = cur;
        }
        for (int i = 0; i != m+n;i++) {
            nums1[i] = sorted[i];
        }
    }
}
