package com.common.jdk.jvm;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoucg
 * @date 2021-02-03 15:06
 */
public class WeakReferencesDemo {





    public static void main(String[] args) {

        // 创建一个引用队列
        ReferenceQueue<Person> referenceQueue = new ReferenceQueue<>();

        Person person1 = new Person();
        person1.setName("zhoucg1");
        person1.setAge("23");

        Person person2 = new Person();
        person2.setName("zhoucg2");
        person2.setAge("24");

        Person person3 = new Person();
        person3.setName("zhoucg3");
        person3.setAge("25");

        // 加入三个弱引用
        List<WeakReference<Person>> lists = new ArrayList<>();
        lists.add(new WeakReference<>(person1));
        lists.add(new WeakReference<>(person2));
        lists.add(new WeakReference<>(person3));


        // 实际上，上面中的person1 .. 2 都是由对应的结果，所有都是不能够GC掉的
        // 如果加上这个，会变成null
        person1 = null;
        person2 = null;
        person3 = null;
        System.gc();
        for (WeakReference<Person> personSingle : lists) {
            // 输出对应的null
            System.out.println(personSingle.get());
        }






        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            byte[] buff = new byte[1024 * 1024];
            WeakReference<byte[]> sr = new WeakReference<>(buff);
            //list.add(sr);
            list.add(buff);
        }
        for(int i=0; i < list.size(); i++){
            //Object obj = ((WeakReference) list.get(i)).get();
            System.out.println(list.get(i));
            //System.out.println(obj);
        }
        System.gc(); //主动通知垃圾回收

        for(int i=0; i < list.size(); i++){
            //Object obj = ((WeakReference) list.get(i)).get();
            System.out.println(list.get(i));
        }



        Person person = new Person();
        person.setName("zhoucg");
        person.setAge("23");

        Entry entry = new Entry(person, "1");
        System.gc();
        System.out.println(person);
        person = null;
        //System.gc();
        Person personx = entry.get();
        System.out.println(personx);
    }


    private static class Entry extends WeakReference<WeakReferencesDemo.Person> {

        private String personCount;

        public Entry(WeakReferencesDemo.Person referent, String personCount) {
            super(referent);
            this.personCount = personCount;
        }



        public String getPersonCount() {
            return personCount;
        }

        public void setPersonCount(String personCount) {
            this.personCount = personCount;
        }
    }

    private static class Person {
        /**
         * 名称
         */
        private String name;
        /**
         * 年龄
         */
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age='" + age + '\'' +
                    '}';
        }
    }

    /**
     * 当前的person对象属于虚引用
     */
    private static final class WeakPerson<K> extends WeakReference<K> {

        public WeakPerson(K referent, ReferenceQueue<K> q) {
            super(referent, q);
        }
    }


}
