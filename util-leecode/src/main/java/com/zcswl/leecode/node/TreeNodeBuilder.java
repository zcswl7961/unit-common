package com.zcswl.leecode.node;

/**
 * 构建一个二叉树的结构
 * @author zhoucg
 * @date 2021-04-28 16:25
 */
public class TreeNodeBuilder {


    /**
     *
     *  构建一个二叉搜索树
     *  根节点的值 > 左子树的值，
     *  根节点的值 < 右子树的值
     *         10
     *        / \
     *       9  20
     *      /  /  \
     *     7  15   21
     *    / \
     *   5  8
     *  /
     * 4
     */
    public static TreeNode buildBst() {
        TreeNode root = new TreeNode(10);
        TreeNode left = new TreeNode(9);
        root.left = left;

        TreeNode left1 = new TreeNode(7);
        left.left = left1;

        TreeNode left11 = new TreeNode(5);
        TreeNode right11 = new TreeNode(8);
        left1.left = left11;
        left1.right = right11;

        left11.left = new TreeNode(4);

        TreeNode right12 = new TreeNode(20);
        root.right = right12;

        TreeNode left13 = new TreeNode(15);
        TreeNode right13 = new TreeNode(21);
        right12.left = left13;
        right12.right = right13;


        return root;
    }

}
