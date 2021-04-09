package com.zcswl.leecode;

import com.zcswl.leecode.node.LinkedNode;
import org.jnetpcap.nio.Link;

/**
 * 链表重排序算法
 * 给定链表L0→L1→L2→…→Ln-1→Ln，把链表重新排序为L0→Ln→L1→Ln-1→L2→Ln-2…。要求：①在原来链表的基础上进行排序，即不能申请新的结点；②只能修改结点的next域，不能修改数据域。
 * @author zhoucg
 * @date 2021-04-01 18:44
 */
public class LinkedReSortDemo {


    public static void main(String[] args) {
        LinkedNode linkedNode = getNode();
        // 将一个链表拆成两个，第一个是倒叙，第二个是正序
        LinkedNode[] linkedNodes = resLinkedNode(linkedNode);
        // 对第一个进行反转操作
        LinkedNode fisrt = linkedNodes[0];
        LinkedNode release = releasev(fisrt);
        LinkedNode second = linkedNodes[1];

        // 然后再交叉输出结果
        LinkedNode result = result(second, release);

    }

    private static LinkedNode result(LinkedNode second, LinkedNode release) {
        LinkedNode weakHead = new LinkedNode(-1);
        int i = 0;
        while (release != null) {
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
        if (second != null)
            weakHead.next = second;
        return weakHead.next;
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
        return new LinkedNode(1);
    }
}
