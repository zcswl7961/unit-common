package com.zcswl.leecode;

import com.zcswl.leecode.node.LinkedNode;
import org.jnetpcap.nio.Link;

/**
 * 合并k个顺序列表
 * @author zhoucg
 * @date 2021-04-07 18:04
 */
public class 拼接k个有序列表 {

    public LinkedNode mergeKLists(LinkedNode[] lists) {
        LinkedNode ans = null;
        for (int i = 0; i < lists.length ; i++) {
            ans = mergeTwoLists(ans, lists[i]);
        }
        return ans;
    }

    public LinkedNode mergeTwoLists(LinkedNode a, LinkedNode b) {
        if (a == null || b == null) {
            return a == null ? b : a;
        }
        LinkedNode head = new LinkedNode(0);
        LinkedNode tail = head;
        while (a != null && b != null) {
            if (a.val < b.val) {
                tail.next = a;
                tail = tail.next;
                a = a.next;
            } else {
                tail.next = b;
                tail = tail.next;
                b = b.next;
            }
        }
        if (a == null) {
            tail.next = b;
        }else  {
            tail.next = a;
        }
        return head.next;
    }

}
