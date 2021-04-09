package com.zcswl.leecode;

import com.zcswl.leecode.node.LinkedNode;

/**
 * 拼接两个有序的链表数据
 * @author zhoucg
 * @date 2021-04-07 17:45
 */
public class MergeTwoLists {


    // 递归的思想策略
    private LinkedNode mergeTwoLists(LinkedNode l1, LinkedNode l2) {
        LinkedNode dummyHead = new LinkedNode(0);
        LinkedNode cur = dummyHead;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                cur = cur.next;
                l1 = l1.next;
            } else {
                cur.next = l2;
                cur = cur.next;
                l2 = l2.next;
            }
        }
        // 任一为空，直接连接另一条链表
        if (l1 == null) {
            cur.next = l2;
        } else {
            cur.next = l1;
        }
        return dummyHead.next;
    }
}
