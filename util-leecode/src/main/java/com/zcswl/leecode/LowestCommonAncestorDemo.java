package com.zcswl.leecode;

import com.zcswl.leecode.node.TreeNode;

/**
 * 二叉树的最近公共祖先
 * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/
 *
 *
 * @author zhoucg
 * @date 2021-04-14 9:54
 */
public class LowestCommonAncestorDemo {

    // 递归
    /*
    * 根据以上定义，若 root 是 p, qp,q 的 最近公共祖先 ，则只可能为以下情况之一：
    *   p 和 q 在 root 的子树中，且分列 root 的 异侧（即分别在左、右子树中）；
    *   p=root ，且 q 在 root 的左或右子树中；
    *   q=root , 且 p 在root的左或右子树中；
    *
    * 这样理解可能更加清楚一点： lowestCommonAncestor这个函数不要理解为找公共祖先，而就理解为帮两个节点找祖先 传入的值是root, p, q，帮p和q找到一个祖先就行，找到两个就更好了，如果找不到就返回NULL 在root->left里面找一次，root->right里面再找一次，
    * 如果某一边返回值是NULL， 那么说明两个值都在另一边 由于找的时候，一定是找的最近的祖先返回，所以这里直接返回前面的返回值就行了，可以保证是最近的公共祖先 如果左右的返回值都不是NULL，那说明p和q分别在两边，则当前节点就是最近公共祖先 左右都找不到就直接返回NULL
    *
    * */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 当越过叶子节点，直接返回null
        if (root == null) {
            return null;
        }
        // 递归的终止条件
        // 当p =root 或者是p == root 直接返回root
        if (root == p || root == q) {
            return root;
        }
        // 递归工作 找到root中的左子节点中的left
        // 找到root对应的右子节点 right
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p ,q);
        if (left != null && right != null) {
            return root;
        }
        if (left != null) {
            return left;
        }
        return right;
    }
}
