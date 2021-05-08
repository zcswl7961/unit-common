package com.zcswl.leecode;

/**
 * https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray/
 *
 * 给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度。
 *
 * 输入：
 * A: [1,2,3,2,1]
 * B: [3,2,1,4]
 * 输出：3
 * 解释：
 * 长度最长的公共子数组是 [3, 2, 1] 。
 *
 * 滑动窗口的解法：
 *
 * @author zhoucg
 * @date 2021-05-08 15:49
 */
public class 最长重复子数组 {

    // 使用滑动窗口的策略进行解决
    public int findLength(int[] a, int[] b) {
        // 首先获取到对应两个数组的长度
        int n = a.length;
        int m = b.length;
        int ret = 0;
        for (int i = 0; i < n; i++) {
            //
            int len = Math.min(m, n - i);
            int maxLength = maxLength(a, b, i, 0, len);
            ret = Math.max(ret, maxLength);
        }
        for (int i = 0; i < m; i++) {
            int len = Math.min(n, m - i);
            int maxlen = maxLength(a, b, 0, i, len);
            ret = Math.max(ret, maxlen);
        }
        return ret;
    }

    private int maxLength(int[] a, int[] b, int addA, int addB, int len) {
        int ret = 0, k = 0;
        for (int i = 0; i < len; i++) {
            if (a[addA + i] == b[addB + i]) {
                k++;
            } else {
                k = 0;
            }
            ret = Math.max(ret, k);
        }
        return ret;
    }


    // 官网的另一个评论的解法
    // https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray/solution/wu-li-jie-fa-by-stg-2/
    public int findLength1(int[] a, int [] b) {
        return a.length < b.length ? findMax(a, b) : findMax(b, a);
    }

    private int findMax(int[] a, int[] b) {
        // 即第二个数组的长度是要大于第一个数组的长度
        int max = 0;
        int an = a.length, bn = b.length;
        for (int len = 1; len <= an;  len ++) {
            // 第一个数组是从前往后进行比较，第二是按照从后往前进行比较的
            // a: 3,2,1,4
            // b: 1,2,3,2,1
            // len = 2 的时候 j = 3
            max = Math.max(max, maxLen(a, 0, b, bn - len, len));
        }
        for (int j = bn - an; j>=0 ;j--) {
            max = Math.max(max, maxLen(a, 0, b, j, an));
        }
        for(int i=1;i<an;i++) {
            max = Math.max(max, maxLen(a, i, b, 0, an - i));
        }
        return max;
    }

    int maxLen(int[] a, int i, int[] b, int j, int len) {
        int count = 0, max = 0;
        for(int k = 0; k < len; k++) {
            if(a[i+k] == b[j+k]) {
                count++;
            } else if(count > 0) {
                max = Math.max(max, count);
                count = 0;
            }
        }
        return count > 0 ? Math.max(max, count) : max;
    }

}
