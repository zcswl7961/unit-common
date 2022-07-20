package com.common.jdk.thread.concurrent;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * @author zhoucg
 * @date 2020-12-04 22:39
 */
public class WebsemaphoreTest {

    static LinkedList<Integer> integerLinkedList = new LinkedList<>();
    static Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    produce();
                } catch (InterruptedException e) {
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consume();
                } catch (InterruptedException e) {
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

    }


    private static void produce() throws InterruptedException {
        semaphore.acquire();
        int value = 0;
        while (true) {
            while (integerLinkedList.size() == 10) {
                semaphore.release();
            }

            integerLinkedList.add(value++);


        }

    }

    private static void consume() throws InterruptedException {
        semaphore.acquire();
        while (true) {
            while (integerLinkedList.size() == 0) {
                semaphore.release();
            }
            //semaphore.release();
            Integer value = integerLinkedList.removeFirst();
            System.out.println("Size of the List is " + integerLinkedList.size() + " and value removed is " + value);
            semaphore.release();

            Thread.sleep(100);
        }
    }
}
