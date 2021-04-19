package com.zcswl.leecode;

import com.zcswl.leecode.node.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 *         3
 *        / \
 *       9  20
 *      /  /  \
 *     8  15   7
 *    / \
 *   5  10
 *  /
 * 4
 * @author zhoucg
 * @date 2021-04-16 14:56
 */
public class 二叉树的前序中序后序遍历 {


    public static void main(String[] args) {
        TreeNode root = getNode();
        // 前序遍历：3 9 8 5 4 10 20 15 7
        // 中序遍历：4 5 8 10 9 3 15 20 7
        /*
            root_pointer = 5
            1, 0+5, 0 , 4
         * deep(pre, in, pLeft +1, pLeft + root_pointer, iLeft,  root_pointer - 1); left
           deep(pre, in  pLeft+ root_pointer + 1, pRight, root_pointer+1, iRight); right
         */


        // 前序left   中序left
        // 前序right  中序right
        // 找到对应的位置 root_index = 5
        // 找到对应的左节点的数目 left_count = root_index - 中序left(inorder_left)
        // pLeft+1, preorder_left + left_count
        // root_index + 1, inorder_right

        after(root);


    }

    private static Map<Integer, Integer> indexMap;

    private static TreeNode myBuildTree(int[] preorder, int[] inorder) {
        // 前序遍历：3 9 8 5 4 10 20 15 7
        // 中序遍历：4 5 8 10 9 3 15 20 7
        // 对应的中序定位位置：5
        // 只要我们在中序遍历中定位到根节点，那么我们就可以分别知道左子树和右子树中的节点数目
        // 由于同一颗子树的前序遍历和中序遍历的长度显然是相同的，因此我们就可以对应到前序遍历的结果中，对上述形式中的所有左右括号进行定位。
        int n = preorder.length;
        indexMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            indexMap.put(inorder[i], i);
        }

        return myBuildTree(preorder, inorder,0, n-1, 0, n-1);

    }

    public static TreeNode myBuildTree(int[] preorder, int[] inorder, int preorder_left, int preorder_right, int inorder_left, int inorder_right) {
        if (preorder_left > preorder_right) {
            return null;
        }
        // 前序遍历中的第一个节点就是根节点
        int preorder_root = preorder_left;

        // 在中序遍历中定位根节点
        int inorder_root = indexMap.get(preorder[preorder_root]);

        // 先把根节点建立起来
        TreeNode root = new TreeNode(preorder[inorder_root]);
        // 得到左子树的数量
        int size_left_subtree = inorder_root - inorder_left;

        // 递归的构造左子树，并连接到根节点
        // 先序遍历中「从 左边界+1 开始的 size_left_subtree」个元素就对应了中序遍历中「从 左边界 开始到 根节点定位-1」的元素
        root.left = myBuildTree(preorder, inorder, preorder_left + 1, preorder_left + size_left_subtree, inorder_left, inorder_root - 1);

        // 递归地构造右子树，并连接到根节点
        // 先序遍历中「从 左边界+1+左子树节点数目 开始到 右边界」的元素就对应了中序遍历中「从 根节点定位+1 到 右边界」的元素
        root.right = myBuildTree(preorder, inorder, preorder_left + size_left_subtree + 1, preorder_right, inorder_root + 1, inorder_right);

        return root;
    }



    /**
     *         3
     *        / \
     *       9  20
     *      /  /  \
     *     8  15   7
     *    / \
     *   5  10
     *  /
     * 4
     * 先遍历根节点；
     * 随后递归地遍历左子树；
     * 最后递归地遍历右子树。
     * 3 9 8 5 4 10 20 15 7
     */
    private static void pre(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.println(root.val);
        pre(root.left);
        pre(root.right);
    }

    /**
     * 4 5 8 10 9 3 15 20 7
     * 先递归地遍历左子树；
     * 随后遍历根节点；
     * 最后递归地遍历右子树。
     */
    private static void mid(TreeNode root) {
        if (root == null) {
            return;
        }
        mid(root.left);
        System.out.println(root.val);
        mid(root.right);

    }

    /**
     *         3
     *        / \
     *       9  20
     *      /  /  \
     *     8  15   7
     *    / \
     *   5  10
     *  /
     * 4
     * 先递归地遍历左子树；
     * 随后递归地遍历右子树。
     * 最后遍历根节点
     * 4 5 10 8 9  15 7 20 3
     */
    private static void after(TreeNode root) {
        if (root == null) {
            return;
        }
        after(root.left);
        after(root.right);
        System.out.println(root.val);
    }



    private static TreeNode getNode() {
        TreeNode root = new TreeNode(3);

        TreeNode left = new TreeNode(9);
        TreeNode right = new TreeNode(20);
        root.left = left;
        root.right = right;

        right.left = new TreeNode(15);
        right.right = new TreeNode(7);

        TreeNode left2 = new TreeNode(8);
        left.left = left2;

        TreeNode left3 = new TreeNode(5);
        TreeNode right3 = new TreeNode(10);
        left2.left = left3;
        left2.right = right3;

        left3.left = new TreeNode(4);

        return root;



    }
}
