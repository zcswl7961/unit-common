package com.zcswl.leecode;

import com.zcswl.leecode.node.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author zhoucg
 * @date 2021-04-28 15:32
 */

public class 求根到叶子节点数字之和 {

    // 深度优先遍历
    public int sum(TreeNode treeNode) {
        return dfs(treeNode, 0);
    }

    private int dfs(TreeNode root, int prevSum) {
        if (root == null) {
            return 0;
        }
        int sum = prevSum * 10 + root.val;
        if (root.left == null && root.right == null) {
            return sum;
        } else {
            return dfs(root.left, sum) + dfs(root.right, sum);
        }
    }


    // 广度优先遍历
    // 定义两个队列

    /**
     * nodeQueue:存放对应的节点的值
     * numQueue： 存放对应的上一个节点的数据
     *      1
     *  2       3
     *  12 + 13
     */
    public int sum1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int sum = 0;
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> numQueue = new LinkedList<>();
        nodeQueue.offer(root);
        numQueue.offer(root.val);
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            int num = numQueue.poll();
            TreeNode left = node.left, right = node.right;
            // 当对应的节点不存在对应的左子树，也不存在对应的右子树的时候，才进行相加操作
            if (left == null && right == null) {
                sum += num;
            } else {
                if (left != null) {
                    nodeQueue.offer(left);
                    numQueue.offer(num * 10 + left.val);
                }
                if (right != null) {
                    nodeQueue.offer(right);
                    numQueue.offer(num * 10 + right.val);
                }
            }

        }
        return sum;

    }
}
