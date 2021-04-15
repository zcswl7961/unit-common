package com.zcswl.leecode;

import com.zcswl.leecode.node.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树的右视图
 *          1
 *      2      3
 *   4
 *   if（root.right != null） {
 *       // 加上去
 *   }
 * @author zhoucg
 * @date 2021-04-15 10:17
 */
public class RightSideViewDemo {


    /**
     * 这道题有一个解法是二叉树按层遍历，然后取每一个数组的最后一个数字
     */
    private static List<Integer> rightSideView(TreeNode treeNode) {
        if (treeNode == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> levels = new ArrayList<>();
        deepLevel(treeNode, 0, levels);
        List<Integer> result = new ArrayList<>();
        for (List<Integer> l : levels) {
            result.add(l.get(l.size() - 1));
        }
        return result;

    }

    private static void deepLevel(TreeNode treeNode, int level, List<List<Integer>> levels) {
        if (level == levels.size()) {
            levels.add(new ArrayList<>());
        }
        levels.get(level).add(treeNode.val);
        if (treeNode.left != null) {
            deepLevel(treeNode.left, level + 1, levels);
        }
        if (treeNode.right != null) {
            deepLevel(treeNode.right, level + 1, levels);
        }
    }


    /**
     *  二叉树的右视图
     *  第二种解法，使用队列的策略
     */
    private static List<Integer> rightSideView1(TreeNode node) {
        List<Integer> res = new ArrayList<>();
        if (node == null) {
            return res;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0 ;i < size; i++) {
                TreeNode node1 = queue.poll();
                if (node1.left != null) {
                    queue.add(node1.left);
                }
                if (node1.right != null) {
                    queue.add(node1.right);
                }
                if (i == size - 1) {
                    res.add(node1.val);
                }
            }
        }
        return res;
    }
}
