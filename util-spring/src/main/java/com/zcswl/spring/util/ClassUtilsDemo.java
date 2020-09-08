package com.zcswl.spring.util;

import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoucg
 * @date 2020-08-27 16:34
 */
public class ClassUtilsDemo {

    public static void main(String[] args) {
        String intName = int.class.getName();
        String booleanStrName = boolean[].class.getName();
        String byteStrName =  byte[].class.getName();
        ClassLoader clToUse = null;
        test(clToUse);


        Map<String, String> result = new HashMap<>();
        testMap(result);

        String s = "zhoucg";

        testStr(s);

        System.out.println(s);

        System.out.println(result);

        if (clToUse == null) {
            clToUse = Thread.currentThread().getContextClassLoader();
        }

        System.out.println(clToUse);

        System.out.println(intName);


        boolean javaLanguageInterface = ClassUtils.isJavaLanguageInterface(ClassUtilsDemo.class);


        Method test = ClassUtils.getMethod(ClassUtilsDemo.class, "test", ClassLoader.class);
    }


    public static void test(ClassLoader classLoader) {
        if (classLoader == null) {
            classLoader = Thread.currentThread().getContextClassLoader();
        }

    }


    private static void testMap(Map<String, String> result) {
        result.put("1","2");
    }

    private static void testStr(String s) {
        s = "w";
    }
}
