package com.zcswl.leecode;

import com.zcswl.leecode.node.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 完全二叉树：
 *      若设二叉树的深度为 h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，
 *      第 h 层所有的结点都连续集中在最左边，这就是完全二叉树。（注：第 h 层可能包含 1~ 2h 个节点。）
 * @author zhoucg
 * @date 2021-04-27 10:43
 */
public class 二叉树的完全性检验 {


    public static boolean isCompleteTree(TreeNode treeNode) {
        // 对于一个完全二叉树，对于按照层序遍历过程中遇到第一个空节点之后不应该在出现非空节点
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(treeNode);

        boolean reachedEnd  = false;

        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (reachedEnd && cur != null) {
                return false;
            }
            if (cur == null) {
                reachedEnd = true;
                continue;
            }
            queue.offer(cur.left);
            queue.offer(cur.right);
        }
        return true;
    }
}
