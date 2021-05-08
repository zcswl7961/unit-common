package com.zcswl.leecode;

import com.zcswl.leecode.node.LinkedNode;

/**
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/
 *
 * 删除按照升序链表重复的数据
 *
 * @author zhoucg
 * @date 2021-05-08 16:27
 */
public class 删除排序链表中的重复元素 {

    public static void main(String[] args) {
        LinkedNode root = new LinkedNode(1);
        LinkedNode root1 = new LinkedNode(1);
        LinkedNode root2 = new LinkedNode(1);
        root.next = root1;
        root1.next = root2;

        deleteDuplicates(root);
    }

    public static LinkedNode deleteDuplicates(LinkedNode head) {

        if (head == null) {
            return null;
        }
        // 设置对应的暂存数据
        int tmp = head.val;
        LinkedNode root = head;
        while (root.next != null) {
            // 获取对应的当前值的下一个元素
            LinkedNode net = root.next;
            if (net.val == tmp) {
                // 重复数据进行删除操作
                // 删除了之后还是要拿第一个进行比较。。。。。。。
                root.next = net.next;
            } else {
                root = root.next;
                tmp = root.val;
            }

        }
        return head;
    }
}
