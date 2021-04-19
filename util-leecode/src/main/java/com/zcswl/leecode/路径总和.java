package com.zcswl.leecode;

import com.zcswl.leecode.node.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum ，判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。
 *
 * 叶子节点 是指没有子节点的节点。
 * @author zhoucg
 * @date 2021-04-19 11:21
 */
public class 路径总和 {

    /**
     * 递归
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return sum == root.val;
        }
        return hasPathSum(root.left, sum - root.val ) || hasPathSum(root.right, sum - root.val);

    }


    /**
     * 广度优先搜索
     * 使用队列的策略
     */
    public boolean hasPathSum1(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        // 队列的结果
        Queue<TreeNode> queNode = new LinkedList<TreeNode>();
        // 结果值
        Queue<Integer> queVal = new LinkedList<Integer>();

        queNode.offer(root);
        queVal.offer(root.val);
        while (!queNode.isEmpty()) {
            TreeNode now = queNode.poll();
            int temp = queVal.poll();
            if (now.left == null && now.right == null) {
                if (temp == sum) {
                    return true;
                }
                continue;
            }
            if (now.left != null) {
                queNode.offer(now.left);
                queVal.offer(now.left.val + temp);
            }
            if (now.right != null) {
                queNode.offer(now.right);
                queVal.offer(now.right.val + temp);
            }
        }
        return false;
    }

    // 使用栈的策略
    public boolean hasPathSum2(TreeNode node, int sum) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();//出栈

            //累加到叶子节点之后，结果等于sum，说明存在这样的一条路径
            if (cur.left == null && cur.right == null) {
                if (cur.val == sum)
                    return true;
            }

            if (cur.right != null) {
                cur.right.val = cur.val + cur.right.val;
                stack.push(cur.right);
            }

            //左子节点累加，然后入栈
            if (cur.left != null) {
                cur.left.val = cur.val + cur.left.val;
                stack.push(cur.left);
            }

        }
        return false;
    }
}
