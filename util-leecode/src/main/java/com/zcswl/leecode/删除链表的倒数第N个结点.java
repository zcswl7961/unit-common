package com.zcswl.leecode;

import com.zcswl.leecode.node.LinkedNode;

/**
 * 删除链表的倒数第N个节点
 *
 * @author zhoucg
 * @date 2021-04-29 14:59
 */
public class 删除链表的倒数第N个结点 {

    public static void main(String[] args) {
        //LinkedNode root = LinkedNodeBuilder.build();
        LinkedNode x = new LinkedNode(1);
        LinkedNode linkedNode = removeNthFromEnd(x, 1);
        while (linkedNode != null) {
            System.out.print(linkedNode.val);
            linkedNode = linkedNode.next;
        }
    }

    public static LinkedNode removeNthFromEnd(LinkedNode root, int n) {
        // 你能实现只使用一趟扫描实现嘛
        // -1 -> 1 -> 2 -> 3 -> 4 -> 5
        if (root == null) {
            return null;
        }
        LinkedNode root1 = new LinkedNode(-1);
        root1.next = root;
        // 设置对应的快指针
        LinkedNode slow = root1;
        LinkedNode fast = root1;
        for (int i = 0; i < n;i++) {
            // 如果对应的fast未null
            if (fast == null) {
                return null;
            }
            fast = fast.next;
        }
        // 如果对应的fast 小于对应的结果
        while(fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        // 3 5 的操作, 然后找到3后面的数据
        slow.next = slow.next.next;
        return root1.next;


    }



}
