package com.zcswl.leecode;

import com.zcswl.leecode.node.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/binary-tree-inorder-traversal/
 *
 * 二叉树的中序遍历
 * @author zhoucg
 * @date 2021-04-19 9:43
 */
public class 二叉树的中序遍历 {

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        // 中序遍历 ， 左-> 中 ->右
        mid(result, root);
        return result;
    }

    /**
     * 递归的思想
     */
    public void mid(List<Integer> result, TreeNode root) {
        if (root == null) {
            return;
        }
        mid(result, root.left);
        result.add(root.val);
        mid(result, root.right);
    }

    /**
     * 迭代算法
     * 迭代的核心就是使用栈的策略，先进后出的原理，push /pop，中序遍历
     *
     * 先左子树，在中节点  再右子树
     */
    public List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        LinkedList<TreeNode> stk = new LinkedList<>();

        while (root != null || !stk.isEmpty()) {
            while (root != null) {
                stk.push(root);
                root = root.left;
            }
            root = stk.pop();
            res.add(root.val);
            root = root.right;
        }
        return res;
    }
}
