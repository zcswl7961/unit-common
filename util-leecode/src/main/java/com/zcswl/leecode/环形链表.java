package com.zcswl.leecode;

import com.zcswl.leecode.node.LinkedNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 判断链表是否为环形链表
 * https://leetcode-cn.com/problems/linked-list-cycle/
 *
 * 一文搞定常见的链表问题：
 * https://leetcode-cn.com/problems/linked-list-cycle/solution/yi-wen-gao-ding-chang-jian-de-lian-biao-wen-ti-h-2/
 * @author zhoucg
 * @date 2021-04-15 15:35
 */
public class 环形链表 {



    public static void main(String[] args) {
        System.out.println("1");
    }

    /**
     *  哈希表的策略
     */
    private static boolean check(LinkedNode head) {
        Set<LinkedNode> seen = new HashSet<>();
        while (head != null) {
            if (!seen.add(head)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    /**
     * 快慢指针的策略
     */
    private static boolean hasCycle(LinkedNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        LinkedNode slow = head;
        LinkedNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;

    }
}
