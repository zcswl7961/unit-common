package com.zcswl.leecode;

import com.zcswl.leecode.node.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/invert-binary-tree/solution/fan-zhuan-er-cha-shu-by-leetcode-solution/
 *
 * 翻转一颗二叉树
 *
 * 我说用递归，面试官让我用循环做，不会...”——2021.4.6 美团-安卓-三面
 *
 * @author zhoucg
 * @date 2021-05-08 15:37
 */
public class 二叉树翻转 {

    // 递归的思路进行翻转操作
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }


    // 层序遍历的翻转
    public TreeNode invertTree1(TreeNode root) {
        if (root == null) {
            return null;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return root;
    }

}
