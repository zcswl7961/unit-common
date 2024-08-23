package com.common.jdk.thread;

/**
 * 每一个线程顺序打印1--100的数字
 * @author xingyi
 * @date 2023/11/9
 */
public class ThreadPrintDemo {

    private static final Object LOCK = new Object();
    private static final Integer MAX = 100;

    private static int count = 0;

    public static void main(String[] args) {
        Thread a = new Thread(new Node(0));
        Thread b = new Thread(new Node(1));
        Thread c = new Thread(new Node(2));
        a.start();
        b.start();
        c.start();

    }

    public static class Node implements Runnable {

        private final int num;

        public Node(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            while (count < MAX) {
                synchronized (LOCK) {
                    try {
                        while (count % 3 != num) {
                            LOCK.wait();
                        }
                        if (count < MAX) {
                            System.out.println("Thread-"+num + " Print " + count);
                        }
                        count++;
                        LOCK.notifyAll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
    }
}
