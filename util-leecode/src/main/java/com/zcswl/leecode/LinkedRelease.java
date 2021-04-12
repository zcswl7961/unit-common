package com.zcswl.leecode;

import com.zcswl.leecode.node.LinkedNode;
import org.jnetpcap.nio.Link;

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

        LinkedNode head1 = getNode();
        LinkedNode linkedNode1 = release(head1, 2, 4);

        while (linkedNode1 != null) {
            System.out.println(linkedNode1.val);
            linkedNode1 = linkedNode1.next;
        }
    }

    /**
     * 链表反转的第一种实现
     * 1 -> 2 -> 3 -> 4 -> 5
     * 1 -> null
     */
    public static LinkedNode release(LinkedNode head) {
        // 指向当前链表的前驱节点
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


    /**
     *  链表反转，反转部分 from to 的链表信息
     *  1->2->3->4->5->null,from=2,to=4
     *  1->4->3->2->5->null
     */
    public static LinkedNode release(LinkedNode head, int from, int to) {
        if (head == null) {
            return null;
        }
        LinkedNode node1 = head;
        int len = 0;
        // 找到对应的是from 前一个节点的即： 2的前一个节点1
        LinkedNode fPre = null;
        // 找到对应的是to 后一个节点，即4 的后一个节点 5
        LinkedNode toPos = null;
        while (node1 != null) {
            len++;
            fPre = len == (from - 1) ? node1 : fPre;
            toPos = len == (to + 1) ? node1 : toPos;
            node1 = node1.next;
        }
        if (from >= to || from < 1 || to > len ){
            return head;
        }
        // fPre的next 节点实际上就是反转的第一个节点
        node1 = (fPre == null) ? head : fPre.next;
        // 反转的起始位置 即从3 开始反转
        LinkedNode node2 = node1.next;
        // 将node1的next指向对应的最后，没有问题，因为反转之后就是这样的
        node1.next = toPos;
        LinkedNode next = null;
        // 这个实际上是链表反转的另一个精髓操作
        while (node2 != toPos) {
            next = node2.next;
            node2.next = node1;
            node1 = node2;
            node2 = next;
        }
        if (fPre != null) {
            fPre.next = node1;
            return  head;
        }
        return node1;


    }


    /**
     * 链表反转的第三种实现
     */
    public static LinkedNode reverse2(LinkedNode node) {

        if (node == null || node.next == null) {
            return node;
        }
        // 我们先把递归的结果保存好，这个的实际意思就是
        LinkedNode newList = reverse2(node.next);

        // 改变 1，2节点的指向。
        // 通过 head.next获取节点2
        LinkedNode t1  = node.next;
        // 让 2 的 next 指向 2
        t1.next = node;
        // 1 的 next 指向 null.
        node.next = null;
        // 把调整之后的链表返回。
        return newList;

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
