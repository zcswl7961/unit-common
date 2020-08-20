package com.zcswl.spring.convert;

import org.springframework.core.MethodParameter;
import org.springframework.core.convert.support.GenericConversionService;

import javax.validation.constraints.NotNull;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;

/**
 * @see org.springframework.core.MethodParameter
 *
 * @author zhoucg
 * @date 2020-07-16 17:24
 */
public class MethodParameTest {

    public static void main(String[] args) throws NoSuchMethodException {

        Method method = MethodParameTest.class.getMethod("method", String.class, Long.TYPE);

        MethodParameter stringParameter = new MethodParameter(method, 0);
        MethodParameter longParameter = new MethodParameter(method,1);

        // -1 for the method return type
        MethodParameter intReturnType = new MethodParameter(method, -1);


        // 判断Class的类型的方式 下面的第一个方法和第二个方法一致
        System.out.println(Executable.class.isInstance(method));
        System.out.println(method instanceof Executable);
        Executable executable = (Executable)method;

        // https://www.jianshu.com/p/662c7c3a5e71
        Class<?>[] parameterTypes = executable.getParameterTypes();

        // 查询当前MethodParameTest类对应的构造函数的第一个参数类型
        MethodParameter methodParameter = MethodParameter.forExecutable(MethodParameTest.class.getConstructor(String.class), 0);
        Class<?> parameterType = methodParameter.getParameterType();
        AnnotatedElement annotatedElement = methodParameter.getAnnotatedElement();
        NotNull annotation = annotatedElement.getAnnotation(NotNull.class);


        GenericConversionService conversionService = new GenericConversionService();
        boolean b = conversionService.canConvert(String.class, Integer.class);

    }


    public int method(String p1, long p2) {
        return 42;
    }


    @NotNull
    public MethodParameTest(String x) {

    }

}
