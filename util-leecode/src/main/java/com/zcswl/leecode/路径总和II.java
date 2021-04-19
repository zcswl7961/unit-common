package com.zcswl.leecode;

import com.zcswl.leecode.node.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * https://leetcode-cn.com/problems/path-sum-ii/
 * @author zhoucg
 * @date 2021-04-19 11:12
 */
public class 路径总和II {

    List<List<Integer>> res = new ArrayList<>();
    List<Integer> list = new ArrayList<>();

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        dfs(root, 0, sum);
        return res;
    }

    public void dfs(TreeNode root, int num, int sum) {
        if (root == null) {
            return;
        }
        num += root.val;
        list.add(root.val);
        // 表示路径根节点
        if(num == sum && root.left == null && root.right == null) res.add(new ArrayList(list));
        dfs(root.left, num, sum);
        dfs(root.right, num, sum);
        // 回溯操作，减去当前对应的值
        list.remove(list.size() - 1);
    }
}
