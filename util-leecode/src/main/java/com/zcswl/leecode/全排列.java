package com.zcswl.leecode;

import java.util.*;

/**
 *
 * https://leetcode-cn.com/problems/permutations/solution/quan-pai-lie-by-leetcode-solution-2/
 * @author zhoucg
 * @date 2021-04-16 22:45
 */
public class 全排列 {


    public static void main(String[] args) {
        int[] nums = {1,2,3};
        List<List<Integer>> permute = permute1(nums);
        System.out.println(permute);

    }

    private static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();

        List<Integer> output = new ArrayList<Integer>();
        for (int num : nums) {
            output.add(num);
        }

        int n = nums.length;
        backtrack(n, output, res, 0);
        return res;


    }

    public static void backtrack(int n, List<Integer> output, List<List<Integer>> res, int first) {
        // 所有数都填完了
        if (first == n) {
            res.add(new ArrayList<Integer>(output));
        }
        for (int i = first; i < n; i++) {
            // 动态维护数组
            Collections.swap(output, first, i);
            // 继续递归填下一个数
            backtrack(n, output, res, first + 1);
            // 撤销操作
            Collections.swap(output, first, i);
        }
    }


    /**
     * 第二种解法，回溯法
     */
    private static List<List<Integer>> permute1(int[] nums) {
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) {
            return res;
        }
        LinkedList<Integer> queue = new LinkedList<Integer>();
        boolean[] used = new boolean[len];
        dfs1(nums, len, queue, used, res);
        return res;

    }

    private static void dfs1(int[] nums, int len, LinkedList<Integer> queue, boolean[] used, List<List<Integer>> res) {
        if (queue.size() == len) {
            res.add(new ArrayList<>(queue));
        }
        for (int i = 0; i < len; i++) {
            if (used[i]) {
                continue;
            }
            queue.addLast(nums[i]);
            used[i] = true;
            dfs1(nums, len, queue, used, res);
            queue.removeLast();
            used[i] = false;
        }
    }
}
