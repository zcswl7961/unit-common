package com.zcswl.leecode;

import javax.swing.*;

/**
 *
 * https://leetcode-cn.com/problems/longest-increasing-subsequence/solution/zui-chang-shang-sheng-zi-xu-lie-by-leetcode-soluti/
 * @author zhoucg
 * @date 2021-04-17 10:38
 */
public class 最长上升子序列 {

    public static void main(String[] args) {
        int[] nums = {0,8,4,12,2};
        System.out.println(lengthOfLIS(nums));
    }

    public static int lengthOfLis(int[] nums) {
        // 动态规划查找最差上升子序列得结果


        int max = 1;
        if (nums == null || nums.length == 0) {
            return max;
        }
        int length = nums.length;
        // 表示得是每一个对应得值得结果
        int[] dp = new int[length];
        // 最短得也是1
        // 10 9 2 5 3 7 101
        // 每一个默认的都是1
        dp[0] = 1;
        for (int i = 1; i < length; i++) {
            for (int j =0;j < i;j++) {
                // 如果对应得当前游标得值，大于遍历对应得前面得值，表示升序，加1的操作
                if (nums[i] > nums[j]) {
                    //  对于dp[i] 的赋值，,在当前值大于前一个值得情况下，和前一个值得结果+1 和当前值取最大 取最大，
                    //  即对应dp每一个存储对应索引下得最大子串长度
                    dp[i] = Math.max(dp[i], dp[j]+1);
                } else {
                    // 如果有值的话，就不需要进行的操作
                    if (dp[i] == 0) {
                        dp[i] = 1;
                    }
                }
                max = Math.max(dp[i], max);
            }
        }

        return max;
    }


    // 贪心算法 + 二分查找算法，这个我确实没有太明白
    public static int lengthOfLIS(int[] nums) {
        int len = 1, n = nums.length;
        if (n == 0) {
            return 0;
        }
        int[] d = new int[n + 1];
        d[len] = nums[0];
        for (int i = 1; i < n; ++i) {
            if (nums[i] > d[len]) {
                d[++len] = nums[i];
            } else {
                int l = 1, r = len, pos = 0; // 如果找不到说明所有的数都比 nums[i] 大，此时要更新 d[1]，所以这里将 pos 设为 0
                while (l <= r) {
                    int mid = (l + r) >> 1;
                    if (d[mid] < nums[i]) {
                        pos = mid;
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
                d[pos + 1] = nums[i];
            }
        }
        return len;
    }
}
