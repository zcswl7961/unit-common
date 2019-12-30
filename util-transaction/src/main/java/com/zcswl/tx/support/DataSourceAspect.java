package com.zcswl.tx.support;

import com.zcswl.tx.DataSourceContextHolder;
import com.zcswl.tx.DataSourceType;
import com.zcswl.tx.annotation.ReadOnly;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;


/**
 * <p>aop 切换，切换当前读写库对应的datasourceManager
 *
 * @author zhoucg
 * @date 2019-12-30 14:42
 */
@Component
@Aspect
public class DataSourceAspect implements Ordered {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Around("@annotation(readOnly)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, ReadOnly readOnly) {


        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature ms = ((MethodSignature) signature);

        try{
            DataSourceContextHolder.set(DataSourceType.READ);
            if(void.class == ms.getReturnType()) {
                proceedingJoinPoint.proceed();
                return null;
            } else {
                return proceedingJoinPoint.proceed();
            }
        } catch (Throwable e) {
            throw new RuntimeException(getMessage(e));
        } finally {
            DataSourceContextHolder.remove();
        }
    }

    private String getMessage(Throwable e){
        String msg = e.getMessage();
        msg += "\n";
        StackTraceElement[] eleArr = e.getStackTrace();
        msg += eleArr[0].toString();
        msg += "\n";
        int length = eleArr.length;
        if (eleArr != null && length > 0){
            if (length > 2){
                msg += eleArr[1].toString();
                msg += "\n";
                msg += eleArr[2].toString();
            }else if (length > 1){
                msg += eleArr[1].toString();
            }
        }

        return msg;
    }
}
