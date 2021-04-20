package com.zcswl.leecode;

import com.zcswl.leecode.node.LinkedNode;
import org.jnetpcap.nio.Link;

/**
 * 链表重排序算法
 * 给定链表L0→L1→L2→…→Ln-1→Ln，把链表重新排序为L0→Ln→L1→Ln-1→L2→Ln-2…。要求：①在原来链表的基础上进行排序，即不能申请新的结点；②只能修改结点的next域，不能修改数据域。
 * @author zhoucg
 * @date 2021-04-01 18:44
 */
public class 链表重排序 {


    public static void main(String[] args) {
        LinkedNode linkedNode = getNode();
        // 将一个链表拆成两个，第一个是倒叙，第二个是正序
        LinkedNode[] linkedNodes = resLinkedNode1(linkedNode);
        // 对第一个进行反转操作
        LinkedNode fisrt = linkedNodes[0];
        LinkedNode release = releasev(fisrt);
        LinkedNode second = linkedNodes[1];

        // 然后再交叉输出结果
        LinkedNode result = result(second, release);

        while (result != null) {
            System.out.println(result.val);
            result = result.next;
        }

    }

    private static LinkedNode result(LinkedNode second, LinkedNode release) {
        LinkedNode weakHead = new LinkedNode(-1);
        LinkedNode pre = weakHead;
        int i = 0;
        while (release != null && second != null) {
            if (i%2 == 0) {
                weakHead.next = second;
                second = second.next;
            } else
            {
                weakHead.next = release;
                release = release.next;
            }
            weakHead = weakHead.next;
            i++;
        }
        if (second != null){
            weakHead.next = second;
        }
        if (release != null) {
            weakHead.next = release;
        }
        return pre.next;
    }

    private static LinkedNode releasev(LinkedNode head) {
        LinkedNode prev = null;
        while (head != null) {
            LinkedNode nex = head.next;
            head.next = prev;
            prev = head;
            head = nex;
        }
        return prev;
    }

    private static LinkedNode[] resLinkedNode1(LinkedNode linkedNode) {
        // 使用快慢指针的策略方式
        // 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8
        LinkedNode fast = linkedNode;
        LinkedNode slow = linkedNode;
        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        LinkedNode release = slow.next;
        slow.next = null;
        LinkedNode[] res = new LinkedNode[2];
        res[0] = release;
        res[1] = linkedNode;
        return res;
    }

    private static LinkedNode[] resLinkedNode(LinkedNode linkedNode) {
        int size = 0;
        LinkedNode tmp = linkedNode;
        while (tmp != null) {
            size++;
            tmp = tmp.next;
        }
        // 链表拆分，操作
        // 1  2 3 4  2
        // 1 2 3 4 5 6
        LinkedNode[] arr = new LinkedNode[2];
        int split = 0;
        int mid = size / 2;
        LinkedNode splitNode = linkedNode;
        while (splitNode != null) {
            if (split == mid -1) {
                arr[0] = splitNode.next;
                // 保持断
                splitNode.next = null;
                break;
            }
            split++;
            splitNode = splitNode.next;
        }
        arr[1] = linkedNode;
        return arr;

    }

    private static LinkedNode getNode() {
        LinkedNode root = new LinkedNode(1);
        LinkedNode l2 = new LinkedNode(2);
        LinkedNode l3 = new LinkedNode(3);
        LinkedNode l4 = new LinkedNode(4);
        LinkedNode l5 = new LinkedNode(5);
        LinkedNode l6 = new LinkedNode(6);
        LinkedNode l7 = new LinkedNode(7);
        LinkedNode l8 = new LinkedNode(8);
        root.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        l5.next = l6;
        l6.next = l7;
        l7.next = l8;

        return root;
    }
}
