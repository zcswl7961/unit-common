package com.zcswl.leecode;

import com.zcswl.leecode.node.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 二叉树按照层排序
 * @author zhoucg
 * @date 2021-04-04 15:49
 */
public class 二叉树按层遍历 {

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

    /**
     * 二叉树按照层进行遍历
     */
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

    /**
     * Queue 队列
     *
     */
    private static void solution1(List<List<Integer>> levels, TreeNode treeNode) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(treeNode);
        while (queue.size() != 0) {
            int length = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0 ;i < length ; i ++ ) {
                TreeNode poll = queue.pollLast();
                level.add(poll.val);
                if (poll.left != null) {
                    queue.addFirst(poll.left);
                }
                if (poll.right != null) {
                    queue.addFirst(poll.right);
                }
            }
            levels.add(level);
        }
    }

}

