package com.zcswl.user.invoke;

import cn.hutool.core.util.ArrayUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * handle the annotation invoke
 * @author xingyi
 * @date 2023/6/19
 */
public class BaseTraceAnnotationHandler {

    @Pointcut(value = "@annotation(com.zcswl.user.invoke.Trace)")
    public void pointCut() {
    }


    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint point) {
        // 1， 获取对应的参数信息
        String kind = point.getKind();
        Object[] args = point.getArgs();

        // 2, 通过对应的类加载获取对应的interceptor数据
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Trace trace = method.getAnnotation(Trace.class);

        // 3, 生成对应的trace数据
        // 生成一个对应的调用traceId，spanId
        beforeMethodInvoke(trace, point);

        Object ret;
        if (ArrayUtil.isNotEmpty(args)) {
            try {
                ret = point.proceed(args);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                ret = point.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        afterMethodInvoke(trace, point);
        return ret;
    }

    public void beforeMethodInvoke(Trace trace, ProceedingJoinPoint point) {

    }

    public void afterMethodInvoke(Trace trace, ProceedingJoinPoint point) {

    }




}
