package com.common.jdk.jvm;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;

/**
 * 利用一个虚引用实现GC通知
 *
 * 引用队列：ReferenceQueue
 * 引用队列可以与软引用、弱引用以及虚引用一起配合使用，当垃圾回收器准备回收一个对象时，
 * 如果发现它还有引用，那么就会在回收对象之前，把这个引用加入到与之关联的引用队列中去。
 * 程序可以通过判断引用队列中是否已经加入了引用，来判断被引用的对象是否将要被垃圾回收，
 * 这样就可以在对象被回收之前采取一些必要的措施。
 *
 * 与软引用、弱引用不同，虚引用必须和引用队列一起使用。
 * @author zhoucg
 * @date 2021-02-03 15:31
 */
public class PhantomReferenceDemo {

    public static volatile boolean isRun = true;

    public static void main(String[] args) throws InterruptedException {


        String abc = new String("abc");
        System.out.println(abc.getClass() + "@" + abc.hashCode());

        // 运用队列
        final ReferenceQueue<String> referenceQueue = new ReferenceQueue<String>();

        new Thread() {
            public void run() {
                while (isRun) {
                    Object obj = referenceQueue.poll();
                    if (obj != null) {
                        try {
                            Field rereferent = Reference.class
                                    .getDeclaredField("referent");
                            rereferent.setAccessible(true);
                            Object result = rereferent.get(obj);
                            System.out.println("gc will collect："
                                    + result.getClass() + "@"
                                    + result.hashCode() + "\t"
                                    + (String) result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();

        PhantomReference<String> abcWeakRef = new PhantomReference<String>(abc, referenceQueue);
        abc = null;
        Thread.currentThread().sleep(3000);
        System.gc();
        Thread.currentThread().sleep(3000);
        isRun = false;

    }




}
