package com.zcswl.user.loader;


import cn.hutool.core.util.ClassUtil;
import com.google.common.collect.Lists;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xingyi
 * @date 2023/6/6
 */
public class ParamNameUtil {


    private static final Set<String> primitiveSimpleWrapperTypeMap = new LinkedHashSet<>();


    private static ClassLoader invokeClassLoader = null;

    public static final String ARRAY_SUFFIX = "[]";

    public static final String LIST_SUFFIX = "<";

    public static final String LIST_AFTER = ">";

    /** Prefix for internal non-primitive array class names: {@code "[L"}. */
    private static final String NON_PRIMITIVE_ARRAY_PREFIX = "[L";

    static  {
        primitiveSimpleWrapperTypeMap.add(Boolean.class.getSimpleName());
        primitiveSimpleWrapperTypeMap.add(boolean.class.getSimpleName());

        primitiveSimpleWrapperTypeMap.add(String.class.getSimpleName());

        primitiveSimpleWrapperTypeMap.add(Byte.class.getSimpleName());
        primitiveSimpleWrapperTypeMap.add(byte.class.getSimpleName());

        primitiveSimpleWrapperTypeMap.add(Character.class.getSimpleName());
        primitiveSimpleWrapperTypeMap.add(char.class.getSimpleName());
        primitiveSimpleWrapperTypeMap.add(Double.class.getSimpleName());
        primitiveSimpleWrapperTypeMap.add(double.class.getSimpleName());

        primitiveSimpleWrapperTypeMap.add(Float.class.getSimpleName());
        primitiveSimpleWrapperTypeMap.add(float.class.getSimpleName());

        primitiveSimpleWrapperTypeMap.add(Integer.class.getSimpleName());
        primitiveSimpleWrapperTypeMap.add(int.class.getSimpleName());

        primitiveSimpleWrapperTypeMap.add(Long.class.getSimpleName());
        primitiveSimpleWrapperTypeMap.add(long.class.getSimpleName());

        primitiveSimpleWrapperTypeMap.add(Short.class.getSimpleName());
        primitiveSimpleWrapperTypeMap.add(short.class.getSimpleName());


    }

    public static List<TypeWrapper> getParamNames(Method method, ClassLoader classLoader) {
        invokeClassLoader = classLoader;
        try {
            return getParameterNames(method);
        } catch (Exception ignore) {
        }
        return Lists.newArrayList();
    }

    private static List<TypeWrapper> getParameterNames(Executable executable) throws ClassNotFoundException {
        List<TypeWrapper> typeWrappers = new ArrayList<>();

        final Parameter[] params = executable.getParameters();
        // String z,boolean b,ObjectWrapper[] c,List<String> d
        for (Parameter param : params) {
            // just check from simple type
            List<TypeWrapper> child = Lists.newArrayList();
            TypeWrapper typeWrapper = TypeWrapper.builder()
                    .paramType(param.getType().getSimpleName())
                    .paramName(param.getName())
                    .child(child)
                    .build();
            wrapper(param.getType(), child, typeWrapper);

            typeWrappers.add(typeWrapper);
        }
        return typeWrappers;
    }

    private static void wrapper(Class<?> param, List<TypeWrapper> child, final TypeWrapper parent) throws ClassNotFoundException {
        String simpleTypeName = param.getSimpleName();
        if (primitiveSimpleWrapperTypeMap.contains(simpleTypeName)) {
            // simple wrapper type
            parent.setArray(false)
                    .setCollect(false)
                    ;
        } else if (simpleTypeName.contains(ARRAY_SUFFIX)){
            parent.setArray(true)
                    .setCollect(false)
                    ;
            String arraySimpleTypeName = simpleTypeName.substring(0, simpleTypeName.lastIndexOf(ARRAY_SUFFIX));
            if (primitiveSimpleWrapperTypeMap.contains(arraySimpleTypeName)) {
                // "java.lang.String[]" style arrays
                parent.setArray(true)
                        .setCollect(false);
            } else {
                // Object[] style arrays
                // "com.xx.ObjectWrapper[]" style arrays
                // [Lcom.xx.ObjectWrap;
                String typeName = param.getName();
                String elementName = typeName.substring(NON_PRIMITIVE_ARRAY_PREFIX.length(), typeName.length() - 1);
                // "com.xx.ObjectWrapper"
                wrapperField(elementName, child);
            }
        } else if ((simpleTypeName.contains(LIST_SUFFIX) && simpleTypeName.contains(LIST_AFTER))
                || Collection.class.isAssignableFrom(param)){
            // 判断对应集合中的自己
            parent.setArray(true)
                    .setCollect(false);
            Type clazz = param.getGenericSuperclass();

            if (clazz == null) {
               // fix class method mark by List<?> or Collection<?> defined
                return;
            }
            ParameterizedType pt = (ParameterizedType)clazz;
            String actualType = pt.getActualTypeArguments()[0].toString();
            if (actualType.equals("E")) {
                // 集合会出现范型擦除
            }
        } else {
            // 对象
            parent.setArray(false)
                    .setCollect(false);

            wrapperField(param.getName(), child);
        }
    }

    private static void wrapperField(String clazzName, List<TypeWrapper> child) throws ClassNotFoundException {
        if (invokeClassLoader == null){
            return;
        }
        Class<?> aClass = invokeClassLoader.loadClass(clazzName);
        Field[] declaredFields = ClassUtil.getDeclaredFields(aClass);
        Arrays.stream(declaredFields)
                .forEach(declaredField -> {
                    List<TypeWrapper> wrappers = Lists.newArrayList();

                    TypeWrapper fieldTypeWrapper = TypeWrapper.builder()
                            .paramName(declaredField.getName())
                            .paramType(declaredField.getType().getSimpleName())
                            .child(wrappers)
                            .build();
                    child.add(fieldTypeWrapper);

                    try {
                        wrapper(declaredField.getType(), wrappers, fieldTypeWrapper);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
    }



    private ParamNameUtil() {
        super();
    }

}


