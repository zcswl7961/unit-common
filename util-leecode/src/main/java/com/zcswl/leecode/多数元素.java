package com.zcswl.leecode;

/**
 * 设计一个时间复杂度 O（n）,空间复杂度为O（1） 得算法机制
 *
 * https://leetcode-cn.com/problems/majority-element/solution/duo-shu-yuan-su-by-leetcode-solution/
 * @author zhoucg
 * @date 2021-04-30 9:43
 */
public class 多数元素 {


    // Boyer-Moore 投票算法
    //   我们维护一个候选众数 candidate 和它出现的次数 count。初始时 candidate 可以为任意值，count 为 0；
    //   我们遍历数组 nums 中的所有元素，对于每个元素 x，在判断 x 之前，如果 count 的值为 0，我们先将 x 的值赋予 candidate，随后我们判断 x：
    //   如果 x 与 candidate 相等，那么计数器 count 的值增加 1；
    //   如果 x 与 candidate 不等，那么计数器 count 的值减少 1
    public int majorityElement(int[] nums) {
        int count = 0;
        Integer candidate  = null;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }
        return candidate;
    }
}
