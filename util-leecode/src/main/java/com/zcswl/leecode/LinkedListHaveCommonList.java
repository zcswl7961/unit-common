package com.zcswl.leecode;

import com.zcswl.leecode.node.LinkedNode;

/**
 * 查询两个链表的公共子序
 * @author zhoucg
 * @date 2021-04-07 9:43
 */
public class LinkedListHaveCommonList {

    public static void main(String[] args) {
        LinkedNode[] linkedNode = getLinkedNode();

        int length1 = getLength(linkedNode[0]);
        int length2 = getLength(linkedNode[1]);

        // 1-> 1 -> 3 -> 4 -> 5
        LinkedNode p1 = linkedNode[0];
        // 1 -> 3-> 4 -> 5
        LinkedNode p2 = linkedNode[1];

        if (length1 > length2) {
            for (int i = 0; i < length1-length2; i++) {
                p1 = p1.next;
            }
        } else {
            for (int i = 0; i < length2-length1; i++) {
                p2 = p2.next;
            }
        }
        while (p1 != null && p2 != null && (p1.val != p2.val)) {
            p1 = p1.next;
            p2 = p2.next;
        }


    }


    private static int getLength(LinkedNode pHead){
        LinkedNode tmp = pHead;
        int count = 0;
        while(tmp != null){
            count++;
            tmp = tmp.next;
        }

        return count;
    }




    private static LinkedNode[] getLinkedNode() {
        LinkedNode[] linkedNodes = new LinkedNode[2];
        // 1-> 1 -> 3 -> 4 -> 5
        LinkedNode linkedNodeA = new LinkedNode(1);
        LinkedNode linkedNodeA1 = new LinkedNode(1);
        LinkedNode linkedNodeA2 = new LinkedNode(3);
        LinkedNode linkedNodeA3 = new LinkedNode(4);
        LinkedNode linkedNodeA4 = new LinkedNode(5);
        linkedNodeA.next = linkedNodeA1;
        linkedNodeA1.next = linkedNodeA2;
        linkedNodeA2.next = linkedNodeA3;
        linkedNodeA3.next = linkedNodeA4;

        // 1 -> 3-> 4 -> 5
        LinkedNode linkedNodeB = new LinkedNode(1);
        LinkedNode linkedNodeB1 = new LinkedNode(3);
        LinkedNode linkedNodeB2 = new LinkedNode(4);
        LinkedNode linkedNodeB3 = new LinkedNode(5);
        linkedNodeB.next = linkedNodeB1;
        linkedNodeB1.next = linkedNodeB2;
        linkedNodeB2.next = linkedNodeB3;


        linkedNodes[0] = linkedNodeA;
        linkedNodes[1] = linkedNodeB;
        return linkedNodes;

    }
}
