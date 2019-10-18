package com.common.jdk.javassist;

import javassist.*;

/**
 *
 * 利用Javassist实现字节码增强时，可以无须关注字节码刻板的结构，其优点就在于编程简单
 * 其中最重要的是ClassPool、CtClass、CtMethod、CtField这四个类：
 *
 * CtClass（compile-time class）：编译时类信息，它是一个class文件在代码中的抽象表现形式，
 *                               可以通过一个类的全限定名来获取一个CtClass对象，用来表示这个类文件。
 * ClassPool：从开发视角来看，ClassPool是一张保存CtClass信息的HashTable，key为类名，value为类名对应的CtClass对象。当我们需要对某个类进行修改时，
 *            就是通过pool.getCtClass(“className”)方法从pool中获取到相应的CtClass。
 *
 * CtMethod、CtField：这两个比较好理解，对应的是类中的方法和属性。
 *
 * @author: zhoucg
 * @date: 2019-10-16
 */
public class JavassistTest {

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException {
        ClassPool cp = ClassPool.getDefault();

        CtClass cc = cp.get("com.common.jdk.javassist.Base");
        CtMethod m = cc.getDeclaredMethod("process");

        m.insertBefore("{ System.out.println(\"start\"); }");
        m.insertAfter("{ System.out.println(\"end\"); }");

        Class c = cc.toClass();

        Base h = (Base)c.newInstance();
        h.process();
    }
}
