package com.common.util.guava;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;

/**
 *
 * ClssToInstanceMap是一种特殊的Map
 * 它的键是类型，而值是符合键所指类型的对象
 * 两个主要的实现类：MutableClassToInstanceMap  ImmutableClassToInstanceMap
 *
 * @author: zhoucg
 * @date: 2019-05-31
 */
public class ClassToInstanceMapUtil {

    public static void main(String[] args) {
        ClassToInstanceMap<Number> numberClassToInstanceMap = MutableClassToInstanceMap.create();

        Integer a = numberClassToInstanceMap.putInstance(Integer.class, Integer.valueOf(0));


        ClassToInstanceMap<ClassBean> classToInstanceMap = MutableClassToInstanceMap.create();
        ClassBean classBean = classToInstanceMap.getInstance(ClassBean.class);
        System.out.println(classBean.getClassName());

    }
}

