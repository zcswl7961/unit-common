package com.zcswl.spring.util;

import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoucg
 * @date 2020-08-27 16:34
 */
public class ClassUtilsDemo {

    public static void main(String[] args) throws ClassNotFoundException {
        String[] strArray = new String[2];
        System.out.println(strArray);

        testClassUtils();

        String intName = int.class.getName();
        System.out.println(intName);
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


    public static void testClassUtils() throws ClassNotFoundException {
        Class<?> anInt = ClassUtils.forName("int", null);
    }

    /**
     * from mybatis-3
     */
    public static Method[] getClassMethods(Class<?> clazz, ClassLoader classLoader) {
        if (classLoader !=  clazz.getClassLoader()) {
            // 报错
        }
        Map<String, Method> uniqueMethods = new HashMap<>();
        Class<?> currentClass = clazz;
        while (currentClass != null && currentClass != Object.class) {
            addUniqueMethods(uniqueMethods, currentClass.getDeclaredMethods());

            Class<?>[] interfaces = currentClass.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                addUniqueMethods(uniqueMethods, anInterface.getMethods());
            }
            currentClass = currentClass.getSuperclass();
        }
        Collection<Method> methods = uniqueMethods.values();
        return methods.toArray(new Method[0]);
    }

    private static void addUniqueMethods(Map<String, Method> uniqueMethods, Method[] methods) {
        for (Method currentMethod : methods) {
            if (!currentMethod.isBridge()) {
                String signature = getSignature(currentMethod);
                // check to see if the method is already known
                // if it is known, then an extended class must have
                // overridden a method
                if (!uniqueMethods.containsKey(signature)) {
                    uniqueMethods.put(signature, currentMethod);
                }
            }
        }
    }

    private static String getSignature(Method method) {
        StringBuilder sb = new StringBuilder();
        Class<?> returnType = method.getReturnType();
        if (returnType != null) {
            sb.append(returnType.getName()).append('#');
        }
        sb.append(method.getName());
        Class<?>[] parameters = method.getParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            sb.append(i == 0 ? ':' : ',').append(parameters[i].getName());
        }
        return sb.toString();
    }


    private static void testMap(Map<String, String> result) {
        result.put("1","2");
    }

    private static void testStr(String s) {
        s = "w";
    }
}
