package com.zcswl.leecode;

import com.zcswl.leecode.node.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树的锯齿排序
 * 使用双端队列的方式进行操作
 * @author zhoucg
 * @date 2021-04-07 13:53
 */
public class ZigzagLevelOrder {

    public static void main(String[] args) {
        TreeNode node = getNode();
        zigzagLevelOrder(node);
    }


    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;

        /**
         * 1， 设置一个默认表示leftToRight 从左到右的标志
         *  首先将对应的root放进去，
         */
        boolean leftToRight = true;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (queue.size() != 0) {
            int length = queue.size();
            List<Integer> lists = new ArrayList<>();
            for (int i = 0 ; i < length ;i++) {
                if (leftToRight) {
                    TreeNode treeNode = queue.pollLast();
                    if (treeNode.left != null) {
                        queue.addFirst(treeNode.left);
                    }
                    if (treeNode.right != null) {
                        queue.addFirst(treeNode.right);
                    }
                    // 满足 queue -> 20->9
                    lists.add(treeNode.val);
                }else {
                    // 从右到左的方式
                    TreeNode treeNode = queue.pollFirst();
                    if (treeNode.right != null) {
                        queue.addLast(treeNode.right);
                    }
                    if (treeNode.left != null) {
                        queue.addLast(treeNode.left);
                    }
                    // 7-> 15
                    lists.add(treeNode.val);
                }
            }
            leftToRight = !leftToRight;
            ans.add(lists);
        }
        for (List<Integer> list : ans) {
            System.out.println(list);
        }
        return ans;

    }


    private static TreeNode getNode() {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        TreeNode child = new TreeNode(20);
        child.left = new TreeNode(15);
        child.right = new TreeNode(7);
        root.right = child;
        return root;
    }
}
