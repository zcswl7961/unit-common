package com.zcswl.leecode;

import com.zcswl.leecode.node.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 判断一个二叉树是不是对称二叉树
 *                1
 *            2      2
 *          3   4  4   3
 *
 * 从根节点出发，判断根节点是不是对称的，假如根节点对称(左节点的值和右节点的值相等)，
 * 再判断左节点的左节点和右节点的右节点
 * 左节点的右节点和右节点的左节点是不是对称的
 * @author zhoucg
 * @date 2021-04-07 9:18
 */
public class 判断二叉树是不是对称二叉树 {

    public static void main(String[] args) {

        TreeNode node = getNode();
        isSymmetricDeep(node.left, node.right);


    }

    public static boolean isSymmetricDeep(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return left.val == right.val && isSymmetricDeep(left.left, right.right) && isSymmetricDeep(left.right, right.left);
    }

    /**
     * 迭代的思想
     */
    public static boolean isSymmetricDeep1(TreeNode u, TreeNode v) {
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(u);
        q.offer(v);
        while (!q.isEmpty()) {
            u = q.poll();
            v = q.poll();
            if (u == null && v == null) {
                continue;
            }
            if ((u == null || v == null) || (u.val != v.val)) {
                return false;
            }

            q.offer(u.left);
            q.offer(v.right);

            q.offer(u.right);
            q.offer(v.left);
        }
        return true;
    }



    public static TreeNode getNode() {
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(2);
        left.left = new TreeNode(3);
        left.right = new TreeNode(4);

        right.left = new TreeNode(4);
        right.right = new TreeNode(3);

        root.left = left;
        root.right = right;
        return root;
    }




}
