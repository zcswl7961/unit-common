package com.common.util.proxy.reflect;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: zhoucg
 * @date: 2019-05-20
 */
public class ReflectTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //获取当前类的class对象
        Class reflectClass = Class.forName("com.common.util.proxy.reflect.ReflectDemo");
        Class reflectClass1 = ReflectDemo.class;

        //使用无参数的构造函数进行创建
        ReflectDemo reflectDemo = (ReflectDemo)reflectClass.newInstance();
        reflectDemo.print();

        //获取对应有参数的构造函数
        Constructor constructor = reflectClass1.getDeclaredConstructor(String.class);
        constructor.setAccessible(true);
        ReflectDemo reflectDemo1 = (ReflectDemo) constructor.newInstance("测试");
        reflectDemo1.print();

    }


    /**
     * 如果我们不期望获取其父类的字段，则需使用Class类的getDeclaredField/getDeclaredFields方法来获取字段即可
     * 倘若需要连带获取到父类的字段，那么使用Class类的getField/getFields,但是也只能获取到public修饰的的字段
     * @throws NoSuchFieldException
     */
    @Test
    public void fieldTest() throws NoSuchFieldException {
        Class reflectClass = ReflectDemo.class;

        //获取指定字段名称的Field类,注意字段修饰符必须为public而且存在该字段,
        Field field = reflectClass.getField("className");
        System.out.println("field:"+field);

        //获取当前类所字段(包含private字段),注意不包含父类的字段
        Field field1 = reflectClass.getDeclaredField("classType");
        System.out.println("field:"+field1);
    }


    /**
     * Method 提供关于类或接口上单独某个方法（以及如何访问该方法）的信息，
     * 所反映的方法可能是类方法或实例方法（包括抽象方法）。下面是Class类获取Method对象相关的方法：
     *
     * method invoke方法：invoke(Object obj, Object... args)
     * 对带有指定参数的指定对象调用由此Method对象表示的底层方法
     */
    @Test
    public void methodTest () throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class reflectClass = ReflectDemo.class;

        //根据参数获取public的Method,包含继承自父类的方法
        Method method = reflectClass.getMethod("toString");
        System.out.println("method="+method);

        //获取所有public的方法:
        Method[] methods =reflectClass.getMethods();
        for (Method m:methods){
            System.out.println("m::"+m);
        }


        //获取当前类的方法包含private,该方法无法获取继承自父类的method
        Method method1 = reflectClass.getDeclaredMethod("print");
        System.out.println("method1::"+method1);
        //获取当前类的所有方法包含private,该方法无法获取继承自父类的method
        Method[] methods1=reflectClass.getDeclaredMethods();
        for (Method m:methods1){
            System.out.println("m1::"+m);
        }

        method1.invoke(reflectClass.newInstance());


    }
}
