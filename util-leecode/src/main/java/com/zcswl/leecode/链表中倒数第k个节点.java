package com.zcswl.leecode;

import com.zcswl.leecode.node.LinkedNode;

/**
 * @author zhoucg
 * @date 2021-04-23 14:58
 */
public class 链表中倒数第k个节点 {


    public LinkedNode getKthFromEnd(LinkedNode head, int k) {
        // 给定你6个链表的值
        // 1 2 3 4 5 6
        // 求出倒数第三个链表的值 6 -3 3
        LinkedNode tmp = head;
        int length = 0;
        while(tmp != null) {
            length++;
            tmp = tmp.next;
        }
        // 求出对应的链表的长度值
        int nextLengthIndex = length - k;
        int i = 0;
        while (head != null) {
            if (i == nextLengthIndex) {
                return head;
            }
            i++;
            head = head.next;
        }
        return null;
    }

    // 双指针的策略
    public LinkedNode getKthFromEnd1(LinkedNode head, int k) {
        LinkedNode slow = head;
        LinkedNode fast = head;
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}
