package com.common.jdk.jvm;

/**
 * ·<clinit>()方法与类的构造函数（即在虚拟机视角中的实例构造器<init>()方法）不同，它不需要显
 * 式地调用父类构造器，Java虚拟机会保证在子类的<clinit>()方法执行前，父类的<clinit>()方法已经执行
 * 完毕。
 *
 * <clinit>()方法对于类或接口来说并不是必需的，如果一个类中没有静态语句块，也没有对变量的
 * 赋值操作，那么编译器可以不为这个类生成<clinit>()方法。
 *
 *
 * 这一部分参考：深入理解jvm虚拟机  虚拟机类加载机制  加载 验证 准备 解析  初始化  使用  卸载
 * @author zhoucg
 * @date 2020-10-16 15:04
 *
 *
 */
public class Child extends Parent{


    {
        // 5
        System.out.println("子类Child中的实例构造块");
    }

    static {
        // 2
        System.out.println("子类Child中的静态构造块");
    }

    Child() {
        // 6
        System.out.println("子类Child中的构造函数");
    }
}
