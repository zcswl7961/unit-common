package com.common.util.javassist;

import javassist.*;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author: zhoucg
 * @date: 2019-06-10
 */
public class JavassistTest {

    @Test
    public void test() throws NotFoundException, CannotCompileException, IOException {
        ClassPool classPool = ClassPool.getDefault();
        String mClassName = "com.common.util.javassist.JavassistTest";
        CtClass ctClass = classPool.get(mClassName);
        System.out.println(ctClass);
        //将对应的class文件写到指定盘符下
    }

    @Test
    public void makeClass() throws CannotCompileException, IOException, IllegalAccessException, InstantiationException, NotFoundException, NoSuchMethodException, InvocationTargetException {

        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass("com.common.util.javassist.ClassPoolMakeJavassistTest");

        //为类型设置字段
        CtField field = new CtField(classPool.get(String.class.getName()), "value", ctClass);
        field.setModifiers(Modifier.PRIVATE);
        // 添加getter和setter方法

        ctClass.addMethod(CtNewMethod.setter("setValue", field));
        ctClass.addMethod(CtNewMethod.getter("getValue", field));
        ctClass.addField(field);

        // 为类设置构造器
        // 无参构造器
        CtConstructor constructor = new CtConstructor(null, ctClass);
        constructor.setModifiers(Modifier.PUBLIC);
        constructor.setBody("{}");
        ctClass.addConstructor(constructor);
        // 参数构造器
        constructor = new CtConstructor(new CtClass[] {classPool.get(String.class.getName())}, ctClass);
        constructor.setModifiers(Modifier.PUBLIC);
        constructor.setBody("{this.value=$1;}");
        ctClass.addConstructor(constructor);

        // 为类设置方法
        CtMethod method = new CtMethod(CtClass.voidType, "run", null, ctClass);
        method.setModifiers(Modifier.PUBLIC);
        method.setBody("{System.out.println(\"执行结果\" + this.value);}");
        ctClass.addMethod(method);

        // 加载和执行生成的类
        Class<?> clazz = ctClass.toClass();
        Object obj = clazz.newInstance();
        clazz.getMethod("setValue", String.class).invoke(obj, "hello");
        clazz.getMethod("run").invoke(obj);

        obj = clazz.getConstructor(String.class).newInstance("OK");
        clazz.getMethod("run").invoke(obj);



    }

}
