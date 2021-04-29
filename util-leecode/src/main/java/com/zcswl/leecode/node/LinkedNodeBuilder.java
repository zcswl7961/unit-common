package com.zcswl.leecode.node;

/**
 * @author zhoucg
 * @date 2021-04-29 14:59
 */
public class LinkedNodeBuilder {

    // 1 -> 2 -> 3 -> 4 -> 5
    public static LinkedNode build() {
        LinkedNode linkedNode = new LinkedNode(1);
        LinkedNode linkedNode1 = new LinkedNode(2);
        LinkedNode linkedNode2 = new LinkedNode(3);
        LinkedNode linkedNode3 = new LinkedNode(4);
        LinkedNode linkedNode4 = new LinkedNode(5);
        linkedNode.next = linkedNode1;
        linkedNode1.next = linkedNode2;
        linkedNode2.next = linkedNode3;
        linkedNode3.next = linkedNode4;

        return linkedNode;
    }
}
