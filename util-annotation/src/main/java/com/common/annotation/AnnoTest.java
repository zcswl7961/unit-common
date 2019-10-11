package com.common.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhoucg
 * @date 2018-12-18
 */
public class AnnoTest {


    public static void main(String[] args) throws Exception {
        Class c = Class.forName("com.common.annotation.Anno");

        Method[] method = c.getMethods();


        boolean flag = c.isAnnotationPresent(FirstAnno.class);

        if(flag) {
            FirstAnno first = (FirstAnno)c.getAnnotation(FirstAnno.class);

            System.out.println("fist anno value is ：" + first.value());
        }

        List<Method> list = new ArrayList<Method>();
        for (int i = 0; i < method.length; i++) {
            list.add(method[i]);
        }

        for (Method m : list) {
            MethondAnnotation anno = m.getAnnotation(MethondAnnotation.class);
            if (anno == null)
                continue;

            System.out.println("second annotation's\nname:\t" + anno.name() + "\nurl:\t" + anno.url());
        }

        for (Field f : c.getDeclaredFields()) {
            ValueAnnotation k = f.getAnnotation(ValueAnnotation.class);
            System.out.println("----kitto anno: " + k.value());
        }
    }
}
