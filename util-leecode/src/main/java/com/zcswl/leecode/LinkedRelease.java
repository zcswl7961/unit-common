package com.zcswl.leecode;

import com.zcswl.leecode.node.LinkedNode;

/**
 * 链表反转
 * @author zhoucg
 * @date 2021-04-07 10:14
 */
public class LinkedRelease {

    public static void main(String[] args) {

        // 1 2 3 4 5 6
        // 就是一个对象
        LinkedNode head = getNode();
        /*LinkedNode tmp = head;
        LinkedNode release = release(tmp);
        // tmp 和head都指向了堆中的同一个地址（即通过一个对象）
        // 我们都知道，调用方法的时候，实际上复制了一份堆内存空间的地址，即也指向了这个对象中的信息
        // 实际上head任然是那个1，只是内部把next指针给操作修改了
        while (release != null) {
            System.out.println(release.val);
            release = release.next;
        }*/

        LinkedNode linkedNode = release1(head);
    }

    /**
     * 链表反转的第一种实现
     * 1 -> 2 -> 3 -> 4 -> 5
     * 1 -> null
     */
    public static LinkedNode release(LinkedNode head) {
        LinkedNode prev = null;
        while (head != null) {
            LinkedNode next = head.next ;
            head.next = prev;
            prev = head;
            head = next;
        }
        // 这种反转策略是基于当前节点判断出来的
        // 新生成了一个 prev，最后就是反转的结果
        return prev;
    }

    /**
     * 链表反转的第二种实现
     */
    public static LinkedNode release1(LinkedNode node) {
        LinkedNode prev = node, next = node.next;
        // 使其闭环，否则出现循环操作
        node.next = null;
        while (next != null) {
            LinkedNode nex = next.next;
            next.next = prev;
            prev = next;
            next = nex;
        }
        // 这个node实际上就是链表的第一个，只是再转换的时候，将原来的链表放到了最后一个
        return prev;
    }




    private static LinkedNode getNode() {
        LinkedNode linkedNode = new LinkedNode(1);
        LinkedNode linkedNode1 = new LinkedNode(2);
        LinkedNode linkedNode2 = new LinkedNode(3);
        LinkedNode linkedNode3 = new LinkedNode(4);
        LinkedNode linkedNode4 = new LinkedNode(5);
        LinkedNode linkedNode5 = new LinkedNode(6);

        linkedNode.next = linkedNode1;
        linkedNode1.next = linkedNode2;
        linkedNode2.next = linkedNode3;
        linkedNode3.next = linkedNode4;
        linkedNode4.next = linkedNode5;

        return linkedNode;
    }



}
