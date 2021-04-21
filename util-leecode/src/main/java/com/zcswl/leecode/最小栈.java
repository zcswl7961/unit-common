package com.zcswl.leecode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author zhoucg
 * @date 2021-04-21 18:21
 */
public class 最小栈 {

    LinkNode head;
    LinkNode tail;

    /** initialize your data structure here. */
    public 最小栈() {
        head = new LinkNode(-1);
        tail = new LinkNode(-1);
        head.next = tail;
        tail.prev = head;
    }

    public void push(int val) {
        LinkNode cu = new LinkNode(val);
        LinkNode prev = tail.prev;
        tail.prev = cu;
        cu.next = tail;
        prev.next = cu;
    }

    public void pop() {
        LinkNode prev = tail.prev;
        if (prev != head) {
            LinkNode prevOld = tail.prev.prev;
            prevOld.next = tail;
            tail.prev = prevOld;
        }
    }

    public int top() {
        LinkNode prev = tail.prev;
        if (prev == head) {
            return -1;
        }
        int val = prev.val;
        pop();
        return val;
    }

    public int getMin() {
        int min = 9999;
        LinkNode tmp = head.next;
        while(tmp != tail && tmp != null) {
            if (tmp.val < min) {
                min = tmp.val;
            }
        }
        return min;
    }

    class LinkNode {
        LinkNode next;
        LinkNode prev;
        int val;

        public LinkNode(int val) {
            this.val = val;
        }
    }
    // ====================== 上面的代码显示超出时间限制，真实尴尬 =========================
    // 官方给的解答
    class MinStack {
        Deque<Integer> xStack;
        Deque<Integer> minStack;

        public MinStack() {
            // 使用LinkedList作为链表操作
            xStack = new LinkedList<Integer>();
            minStack = new LinkedList<Integer>();
            // 使用另一个栈作为最小栈
            minStack.push(Integer.MAX_VALUE);
        }

        public void push(int x) {
            // 每次push对应的栈值得时候，都要push最小值栈得值，
            // 取值结果是取栈定得值，然后比最小
            // xStack   push : 1 -1 -2 3
            // minStack push : MAX : 1 -1 -2 -2
            // pop() 删除得时候 3
            xStack.push(x);
            minStack.push(Math.min(minStack.peek(), x));
        }

        public void pop() {
            xStack.pop();
            minStack.pop();
        }

        public int top() {
            return xStack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }

}
