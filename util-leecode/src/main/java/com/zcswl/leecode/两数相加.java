package com.zcswl.leecode;

import com.zcswl.leecode.node.LinkedNode;

/**
 * @author zhoucg
 * @date 2021-04-23 15:31
 */
public class 两数相加 {

    public static void main(String[] args) {
        LinkedNode l1 = getNode1();
        LinkedNode l2 = getNode2();

        LinkedNode linkedNode = addTwoNumbers(l1, l2);
        while (linkedNode != null) {
            System.out.println(linkedNode.val);
            linkedNode = linkedNode.next;
        }


    }

    public static LinkedNode addTwoNumbers(LinkedNode l1, LinkedNode l2) {
        // 表示的是对应的进位的数字
        //9,9,9,9,9,9,9
        //9,9,9,9
        //8 9 9 9 0 0 0 1
        int x = 0;
        // 表示对应的头部信息
        LinkedNode head = new LinkedNode(-1);
        LinkedNode prev = head;
        while (l1 != null || l2 != null) {
            // 判断对应的结果集
            int tmp = 0;
            if (l1 != null && l2 != null) {
                tmp = l1.val + l2.val + x ;
                l1 = l1.next;
                l2 = l2.next;
            } else if (l1 != null && l2 == null) {
                tmp = l1.val + x;
                l1 = l1.next;
            } else {
                tmp = l2.val + x;
                l2 = l2.next;
            }
            LinkedNode cur = new LinkedNode(tmp % 10);
            x = tmp / 10;
            head.next = cur;
            head = head.next;
        }
        if (x > 0) {
            head.next = new LinkedNode(x);
        }
        return prev.next;

    }


    private static LinkedNode getNode1() {
        LinkedNode a = new LinkedNode(9);
        LinkedNode a1 = new LinkedNode(9);
        LinkedNode a2 = new LinkedNode(9);
        LinkedNode a3 = new LinkedNode(9);
        LinkedNode a4 = new LinkedNode(9);
        LinkedNode a5 = new LinkedNode(9);
        LinkedNode a6 = new LinkedNode(9);

        a.next = a1;
        a1.next = a2;
        a2.next = a3;
        a3.next = a4;
        a4.next = a5;
        a5.next = a6;

        return a;
    }

    private static LinkedNode getNode2() {
        LinkedNode a = new LinkedNode(9);
        LinkedNode a1 = new LinkedNode(9);
        LinkedNode a2 = new LinkedNode(9);
        LinkedNode a3 = new LinkedNode(9);

        a.next = a1;
        a1.next = a2;
        a2.next = a3;

        return a;
    }
}
