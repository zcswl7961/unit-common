package com.zcswl.leecode;

import com.zcswl.leecode.node.LinkedNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 环形链表找到对应的入环点的操作
 * @author zhoucg
 * @date 2021-04-19 10:23
 */
public class 环形链表II {


    public LinkedNode detectCycle(LinkedNode head) {
        // 使用set的策略
        LinkedNode pos = head;
        Set<LinkedNode> sets = new HashSet<>();
        while (pos != null) {
            if (sets.contains(pos)) {
                return pos;
            }
            sets.add(pos);
            pos = pos.next;
        }
        return null;
    }


    public LinkedNode detectCycle1(LinkedNode head) {
        // 使用快慢链表的策略
        if (head == null) {
            return null;
        }
        LinkedNode slow = head, fast = head;
        while (true) {
            if (fast == null || fast.next == null) return null;
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                break;
            }
        }
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return fast;

    }
}
