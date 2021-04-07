package com.zcswl.leecode;

import com.zcswl.leecode.node.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树按照层排序
 * @author zhoucg
 * @date 2021-04-04 15:49
 */
public class TreeLevelPrintDemo {

    public static void main(String[] args) {
        List<List<Integer>> levels = new ArrayList<>();
        solution(levels, 0, getNode());

        for (List<Integer> level : levels) {
            System.out.println(level);
        }

    }

    /**
     *         1
     *      2    3
     *    4    5   6
     */
    private static TreeNode getNode() {
        TreeNode treeNode = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        treeNode.left = left;
        treeNode.right = right;

        left.left = new TreeNode(4);
        right.left = new TreeNode(5);
        right.right = new TreeNode(6);
        return treeNode;
    }

    private static void solution(List<List<Integer>> levels, int level, TreeNode node) {
        if (levels.size() == level) {
            levels.add(new ArrayList<>());
        }
        levels.get(level).add(node.val);
        if (node.left != null) {
            solution(levels, level + 1, node.left);
        }
        if (node.right != null) {
            solution(levels, level + 1, node.right);
        }

    }

}

