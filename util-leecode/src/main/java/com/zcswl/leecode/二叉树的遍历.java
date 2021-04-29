package com.zcswl.leecode;

import com.zcswl.leecode.node.TreeNode;
import com.zcswl.leecode.node.TreeNodeBuilder;

import java.util.*;

/**
 * 二叉树的，前序/中序/后序的遍历机制
 * 包括递归和迭代（非递归的思想）
 *
 *  下面的这个树，也是一个二叉搜索树，即对应的根节点的值大于左子树的值，小于右子树的值
 *
 *         10
 *        / \
 *       9  20
 *      /  /  \
 *     7  15   21
 *    / \
 *   5  8
 *  /
 * 4
 * @author zhoucg
 * @date 2021-04-28 16:21
 */
public class 二叉树的遍历 {


    public static void main(String[] args) {
        TreeNode treeNode = TreeNodeBuilder.buildBst();
        List<Integer> values = new ArrayList<>();
        //pre(treeNode, values);
        //preWithIteration(treeNode, values);
        //mid(treeNode, values);
        //midWithIteration(treeNode, values);
        //after(treeNode, values);
        afterWithIteration(treeNode, values);
        for (int value : values) {
            System.out.print(value + "\t");
        }

    }


    // 前序遍历(递归的思想)：先遍历根节点-> 左子树 -> 右子树
    // 10 9 7 5 4 8 20 15 21
    public static void pre(TreeNode root, List<Integer> values) {
        if (root == null) {
            return;
        }
        values.add(root.val);
        pre(root.left, values);
        pre(root.right, values);
    }
    // 前序遍历（迭代的思想）：先遍历根节点-> 左子树 -> 右子树
    // 通过一个栈的操作，push压入栈底，pop，获取栈顶对应的数据
    public static void preWithIteration(TreeNode root, List<Integer> values) {
        Deque<TreeNode> stack = new LinkedList<>();
        if (root != null) {
            stack.push(root);
            while (!stack.isEmpty()) {
                TreeNode p = stack.pop();
                values.add(p.val);
                if (p.right != null) {
                    stack.push(p.right);
                }
                if (p.left != null) {
                    stack.push(p.left);
                }
            }
        }
    }


    // 中序遍历（递归的思想）：左 -> 中 -> 右
    // 4 5 7 8 9 10 15 20 21
    // 对于一个二叉搜索树而言，中序遍历满足从小到大的顺序进行操作
    public static void mid(TreeNode root, List<Integer> values) {
        if (root == null) {
            return;
        }
        mid(root.left, values);
        values.add(root.val);
        mid(root.right, values);
    }


    // 中序遍历的迭代思想： 左 中 右
    public static void midWithIteration(TreeNode root, List<Integer> values) {
        Deque<TreeNode> stack = new LinkedList<>();
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            TreeNode p = stack.pop();
            values.add(p.val);
            root = p.right;
        }
    }



    // 后序遍历递归思想 : 左 右 中
    // 4 5 8 7 9 15	21 20 10
    public static void after(TreeNode root, List<Integer> values) {
        if (root == null) {
            return;
        }
        after(root.left, values);
        after(root.right, values);
        values.add(root.val);
    }


    // 后序遍历迭代思想: 左 右 中
    // 使用双栈的事项
    public static void afterWithIteration(TreeNode root, List<Integer> values) {
        if (root != null) {
            Deque<TreeNode> s1 = new LinkedList<>();
            Deque<TreeNode> s2 = new LinkedList<>();
            s1.push(root);
            while (!s1.isEmpty()) {
                root = s1.pop();
                s2.push(root);
                if (root.left != null) {
                    s1.push(root.left);
                }
                if (root.right != null) {
                    s1.push(root.right);
                }
            }
            while(!s2.isEmpty()){
                values.add(s2.pop().val);
            }
        }
    }





}
