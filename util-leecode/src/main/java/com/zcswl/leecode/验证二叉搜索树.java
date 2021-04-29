package com.zcswl.leecode;

import com.zcswl.leecode.node.TreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 二叉搜索树的左子树都是小于对应的节点，二叉搜索树的右子树都是大于 对应的值
 * @author zhoucg
 * @date 2021-04-28 15:54
 *    2
 * 1     3
 */
public class 验证二叉搜索树 {

    // 中序遍历的非递归写法解决问题
    public boolean isValidBST(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        double inorder = -Double.MAX_VALUE;

        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            // 对一个的是最后一个节点
            root = stack.pop();
            // 如果中序遍历得到的节点的值小于等于前一个 inorder，说明不是二叉搜索树
            if (root.val <= inorder) {
                return false;
            }
            inorder = root.val;
            root = root.right;
        }
        return true;
    }


}
