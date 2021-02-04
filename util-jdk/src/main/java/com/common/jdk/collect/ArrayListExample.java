package com.common.jdk.collect;

import com.google.common.collect.LinkedListMultimap;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * ArrayList LinkedList
 * @author zhoucg
 * @date 2019-10-18
 */
public class ArrayListExample {

    public static void main(String[] args) {
        List<String> list = new ArrayList();

        list.add("A");

        // 非泛型的设置
        List list1 = new ArrayList();
        list1.add(1);
        list1.add("zhoucg");

        // 下面这两个写法的区别(没区别)
        Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
        Object[] DEFAULTANOTHER = new Object[0];
        System.out.println(DEFAULTCAPACITY_EMPTY_ELEMENTDATA.length);

        // exception
        //DEFAULTCAPACITY_EMPTY_ELEMENTDATA[4] = 1;
        //DEFAULTANOTHER[4] = 1;


        List<String> current = new ArrayList<>(3);
        current.add("1");
        current.add("2");
        current.add("3");
        current.add("4");

        // remove
        List<Integer> removeList = new ArrayList<>(5);
        Integer removeObj = 2;
        removeList.add(1);
        removeList.add(2);
        removeList.add(3);
        removeList.add(4);
        removeList.add(5);
        removeList.remove(2); // 掉E remove(int index)
        removeList.remove((Integer) 2); // 调用 boolean remove(Object e);
        removeList.remove(removeObj); // 一样
        System.out.println(removeList);



        // 迭代器 for()
        Iterator<Integer> iterator = removeList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        // LinkedList
        LinkedList<String> linkedList = new LinkedList<>();

        linkedList.offer("wl");

        linkedList.add(5,"zhoucg");

        linkedList.forEach(s -> System.out.println(s));
    }


    /**
     * 正确的删除List数据
     */
    public static void removeList() {

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");

        list.removeIf(item -> item.equals("1"));


    }


}
