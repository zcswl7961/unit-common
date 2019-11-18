package com.zcswl.db.util;

import java.lang.reflect.Method;

/**
 * @author zhoucg
 * @date 2019-11-15 17:39
 */
public class ClassUtil {

    /**
     * 通过类名称或取类
     * @param className
     * @return
     */
    public static final Class getClass(String className) {
        Class cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException ee) {
            throw new RuntimeException("获取类[" + className + "]失败。", ee);
        }
        return cls;
    }

    /**
     * 获取类实例
     * @param className 类名称
     * @param parameterTypes 构造函数参数类型数组
     * @param parameterValues 构造函数参数值数组
     * @return
     * @throws Exception
     */
    public static final Object getInstance(String className, Class[] parameterTypes, Object[] parameterValues) {
        Class cls = getClass(className);
        Object obj = null;
        try {
            if (isNull(parameterTypes) || isNull(parameterValues) || parameterTypes.length != parameterValues.length) {
                obj = cls.newInstance();
            } else {
                cls.getConstructor(parameterTypes).newInstance(parameterValues);
            }
        } catch (Exception ee) {
            throw new RuntimeException("获取[" + className + "]实例失败。");
        }
        return obj;
    }

    /**
     * 获取类实例(通过空构造函数获取实例)
     * @param className
     * @return
     * @throws Exception
     */
    public static final Object getInstance(String className) {
        return getInstance(className, null, null);
    }

    /**
     * 获取方法
     * @param cls 方法所在类
     * @param methodName 方法名称
     * @param parameterTypes 方法参数类型数组
     * @return
     */
    public static final Method getMethod(Class cls, String methodName, Class[] parameterTypes) {
        if (cls == null)
            throw new RuntimeException("获取方法[" + methodName + "]失败：类为空。");
        Method method = null;
        try {
            method = cls.getMethod(methodName, parameterTypes);
        } catch (Exception ee) {
            throw new RuntimeException("方法[" + methodName + "]不存在。");
        }
        return method;
    }

    /**
     * 获取方法
     * @param className 方法所在类名称
     * @param methodName 方法名称
     * @param parameterTypes 方法参数类型数组
     * @return
     */
    public static final Method getMethod(String className, String methodName, Class[] parameterTypes) {
        Class cls = getClass(className);
        return getMethod(cls, methodName, parameterTypes);
    }

    /**
     * 数组为空判断
     * @param obj
     * @return
     */
    private static final boolean isNull(Object[] obj) {
        if (obj == null || obj.length < 1) {
            return true;
        }
        return false;
    }

    /**
     * 方法执行
     * @param method 方法实体
     * @param obj 调用发放的对象
     * @param parameterValues 方法参数值
     * @return
     * @throws Exception
     */
    public static final Object invokeMethod(Method method, Object obj, Object[] parameterValues) {
        Object returnObj = null;
        try {
            returnObj = method.invoke(obj, parameterValues);
        } catch (Exception ee) {
            throw new RuntimeException("执行方法[" + method.getName() + "]失败。", ee);
        }
        return returnObj;
    }
}
