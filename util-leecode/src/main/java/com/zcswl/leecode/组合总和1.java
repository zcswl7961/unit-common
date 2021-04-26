package com.zcswl.leecode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 回溯算法计算组合总和
 * https://leetcode-cn.com/problems/combination-sum/solution/zu-he-zong-he-by-leetcode-solution/
 * @author zhoucg
 * @date 2021-04-23 17:05
 */
public class 组合总和1 {


    List<List<Integer>> ans = new ArrayList<>();
    List<Integer> tempRes = new ArrayList<>();


    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        dfs(0, 0, candidates, target);
        return ans;
    }

    // 回溯算法
    public void dfs(int cur, int sum, int[] candidates, int target) {
        if (sum == target) {
            ans.add(new ArrayList<>(tempRes));
            return;
        }
        for (int i = cur; i < candidates.length; i++) {
            if (sum + candidates[i] <= target) {
                tempRes.add(candidates[i]);
                dfs(i, sum + candidates[i], candidates, target);
                tempRes.remove(tempRes.size() - 1);
            } else {
                // 由于candidates升序排序，如果出现sum > target,直接break即可
                break;
            }

        }
    }
}
