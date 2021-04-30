package com.zcswl.leecode;


import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 * @author zhoucg
 * @date 2021-04-23 16:26
 */
public class 组合总和 {


    public static void main(String[] args) {
        int[] x = {3, 2, 7 , 6};
        sort(x);
        List<List<Integer>> lists = combinationSum(x, 7);
        for (List<Integer> l : lists) {
            System.out.println(l);
        }

    }

    /**
     * 回溯算法
     * candidates = [2,3,6,7], target = 7,
     *   [7],
     *   [2,2,3]
     */
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> combine  = new ArrayList<>();
        dfs(candidates, target, ans, combine, 0);
        return ans;
    }

    /**
     *
     */
    private static void dfs(int[] candidates, int target, List<List<Integer>> ans, List<Integer> combine, int idx) {
        if (idx == candidates.length) {
            return;
        }
        if (target == 0) {
            ans.add(new ArrayList<>(combine));
            return;
        }
        // 直接跳过
        // idx +1 == length 的时候退出来
        //
        dfs(candidates, target, ans, combine, idx + 1);
        // 选择当前数
        if (target - candidates[idx] >= 0) {
            combine.add(candidates[idx]);
            dfs(candidates, target - candidates[idx], ans, combine, idx);
            combine.remove(combine.size() - 1);
        }
    }



    private static void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int index = i;
            for (int j = 0; j < i; j++) {
                if (arr[index] < arr[j]) {
                    index = j;
                }
                int tmp = arr[index];
                arr[index] = arr[i];
                arr[i] = tmp;
            }
        }
    }
}
