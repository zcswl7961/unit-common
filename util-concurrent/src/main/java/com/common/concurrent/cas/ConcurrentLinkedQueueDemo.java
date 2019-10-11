package com.common.concurrent.cas;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by zhoucg on 2019-03-31.
 */
public class ConcurrentLinkedQueueDemo {


    public static void main(String[] args) {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

        queue.offer(1);
        queue.offer(2);



    }
}
