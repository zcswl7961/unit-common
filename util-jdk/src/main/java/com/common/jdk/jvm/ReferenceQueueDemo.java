package com.common.jdk.jvm;


import org.springframework.web.bind.annotation.PathVariable;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * ReferenceQueue结合PhantomReference 使用的时候，poll出来的Reference通过反射是能够获取道对应的引用对象 @link PhantomReferenceDemo
 *
 * ReferenceQueue结合SoftReference 和 WeakReference获取不到，
 *  （同时，它将声明所有以前的弱可达对象都是可完成的。 同时或在稍后的某个时候，它将把那些在引用队列中注册的新批准的弱引用排队。）可以看源码API文档
 * @author zhoucg
 * @date 2021-02-05 15:51
 */
public class ReferenceQueueDemo {

    private static volatile boolean isRun = true;

    public static void main(String[] args) throws InterruptedException {

        ReferenceQueue<Person> referenceQueue = new ReferenceQueue<>();

        // 创建一个线程
        Runnable runnable = () -> {
            System.out.println("开启线程");
            while (isRun) {
                WeakPerson<Person> weakPerson = (WeakPerson<Person>) referenceQueue.poll();
                if (weakPerson != null) {
                    System.out.println(weakPerson);
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();


        Person person = new Person();
        person.setName("zhoucgwl");

        WeakPerson<Person> weakPerson = new WeakPerson<>(person, referenceQueue);
        person = null;
        Thread.sleep(3000L);
        System.gc();
        Thread.sleep(3000L);
        isRun = false;
    }

    private static class Person {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    private static class WeakPerson<K> extends WeakReference<K> {

        public WeakPerson(K referent) {
            super(referent);
        }

        public WeakPerson(K referent, ReferenceQueue<? super K> q) {
            super(referent, q);
        }
    }
}
