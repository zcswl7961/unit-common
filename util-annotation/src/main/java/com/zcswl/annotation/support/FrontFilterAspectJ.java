package com.zcswl.annotation.support;

import com.zcswl.annotation.ParamFilter;
import com.zcswl.annotation.exception.AnnotationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 切面
 * <p>使用类需要显示的加入spring aop，如Spring boot Application.java 增加 @EnableAspectJAutoProxy
 *
 * <p>使用的Controller类中:
 *      <blockquote><pre>
 *          @PostMapping
 *          @ParamFilter(clazz = StreategyDTO.class,param={"name","address"})
 *          public String strategyDeploy(@RequestBody StreategyDTO dto,@PathVariable String type){
 *              ......
 *          }
 *
 *          public class StreategyDTO{
 *
 *              private String name;
 *              private String address;
 *
 *              get set method .....
 *          }
 *      </pre></blockquote>
 *
 * <p>验证的实体类对象需要是一个标准的POJO(DTO)对象。
 * @author zhoucg
 * @date 2019-11-07 9:54
 */
@Component
@Aspect
public class FrontFilterAspectJ {

    /**
     * 过滤操作
     * @param jp 切入点
     * @param ap 注解
     */
    @Before("@annotation(ap)")
    public void filter(JoinPoint jp , ParamFilter ap) {

        Object[] objects = jp.getArgs();
        if(objects == null || objects.length == 0) {
            return;
        }

        String[] params = ap.param();
        if(params == null || params.length == 0) {
            return;
        }

        Class<?> clazz = ap.clazz();
        Optional<Object> optional = Optional.empty();
        for(Object obj : objects) {
            if(obj.getClass() == clazz) {
                optional = Optional.of(obj);
            }
        }
        if(!optional.isPresent()){
            throw new AnnotationException("请求参数中不存在指定校验的参数类型");
        }
        List<String> paramList = Arrays.asList(params);
        for(Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            if(paramList.contains(fieldName)) {
                try{
                    field.setAccessible(true);
                    Object argValue = field.get(optional.get());
                    if(argValue == null) {
                        throw new AnnotationException("必要的参数类型["+fieldName+"]结果不能为null");
                    }
                } catch (IllegalAccessException e) {
                    throw new AnnotationException("必要的参数类型["+fieldName+"]结果不能为null");
                }
            }
        }


    }
}
