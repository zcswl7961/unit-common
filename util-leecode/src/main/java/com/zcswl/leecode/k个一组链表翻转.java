package com.zcswl.leecode;

import com.zcswl.leecode.node.LinkedNode;
import org.jnetpcap.nio.Link;

import java.util.LinkedList;

/**
 * k 个一组翻转链表
 * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * @author zhoucg
 * @date 2021-04-07 10:51
 */
public class k个一组链表翻转 {
    public static void main(String[] args) {

        LinkedNode linkedNode = getNode();
        // 首先是根据指定的链表分段
        // 然后进行反转追加操作
        // 如果不满足的，进行直接操作
       LinkedNode linkedNode1 = reverseKGroup(linkedNode, 2);
        while (linkedNode1 != null) {
            System.out.println(linkedNode.val);
            linkedNode1 = linkedNode1.next;
        }
    }

    // k = 2
    // node 1 ->2 -> 3 -> 4 -> 5 -> 6 -> 7
    private static LinkedNode reverseNode(int k, LinkedNode node) {
        if (k == 1) {
            return node;
        }
        LinkedNode hair = new LinkedNode(0);
        hair.next = node;
        LinkedNode pre = hair;
        while (node != null) {
            // tail : pre 有一个根节点
            LinkedNode tail = pre;
            for (int i = 0; i< k; ++i) {
                tail = tail.next;
                // ?
                if (tail == null) {
                    return hair.next;
                }
            }
            // tail : 2 -> 3 -> 4 -> 5 -> 6 -> 7
            // 分段找后面的
            LinkedNode nex = tail.next;
            // 3 -> 4 -> 5 -> 6 -> 7
            LinkedNode[] reverse = myReverse(node, tail);
            node = reverse[1];
            tail = reverse[0];
            pre.next = node;
            tail.next = nex;
            pre = tail;
            node = tail.next;
        }

        return hair.next;
    }

    private static LinkedNode[] myReverse(LinkedNode head, LinkedNode tail) {
        // head 1 ->2 -> 3 -> 4 -> 5 -> 6 -> 7
        // tail 3 -> 4 -> 5 -> 6 -> 7
        LinkedNode prev = tail.next;
        LinkedNode p = head;
        // prev 4 -> 5 -> 6 -> 7
        // p :  1 ->2 -> 3 -> 4 -> 5 -> 6 -> 7
        while (prev != tail) {
            LinkedNode nex = p.next;
            p.next = prev; // 1 -> 4 -> 5 -> 6 -> 7
            prev = p; // 1 -> 4 -> 5 -> 6 -> 7
            p = nex; // 2 -> 3 -> 4 -> 5 -> 6 -> 7
        }
        // p : 2 -> 1 -> 3 -> 4 -> 5 -> 6 -> 7
        return new LinkedNode[]{tail, head};
        // 3 -> 4 -> 5 -> 6 -> 7
        // 2 -> 1 -> 3 -> 4 -> 5 -> 6 -> 7
    }

    private static LinkedNode getNode() {
        LinkedNode root = new LinkedNode(1);
        LinkedNode node1 = new LinkedNode(2);
        LinkedNode node2 = new LinkedNode(3);
        LinkedNode node3 = new LinkedNode(4);
        LinkedNode node4 = new LinkedNode(5);
        LinkedNode node5 = new LinkedNode(6);
        LinkedNode node6 = new LinkedNode(7);
        root.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        return root;
    }



    // =============== 上面一个方法是在看着有点累

    public  static LinkedNode reverseKGroup(LinkedNode head, int k) {
        if (k==1||head==null)
            return head;
        return helper(head,k-1);
    }

    public static LinkedNode helper(LinkedNode head,int k){
        if (head==null)
            return null;
        // 1 ->2 -> 3 -> 4 -> 5 -> 6 -> 7
        LinkedNode end= head;
        int index;
        for (index=0;index<k;++index){//让end移动到第k个节点上
            if (end==null)
                break;
            end=end.next;
        }
        if (index!=k||end==null)//这种情况下，说明不够k个，直接返回head，即不翻转
            return head;
        // end : 2 -> 3 -> 4 -> 5 -> 6 -> 7
        // node : 3 -> 4 -> 5 -> 6 -> 7
        LinkedNode node = end.next;
        // end.next = null 实上上head 和 start 都变成了 1 -> 2 - null
        end.next=null;//这里先将k个节点与后面的连接断开，方便翻转链表
        reverse(head);//翻转这k个节点
        head.next=helper(node,k);//让前k个节点的头节点的next指向后k个节点的尾节点
        return end;
    }

    /**
     * 翻转链表
     */
    public static LinkedNode reverse(LinkedNode head){
        // 1 -> 2 -> 3 -> null
        LinkedNode pre = head, next = head.next;
        while (next != null) {
            LinkedNode net = next.next;
            next.next = pre;
            pre = next;
            next = net;
        }
        return next;
    }
}
