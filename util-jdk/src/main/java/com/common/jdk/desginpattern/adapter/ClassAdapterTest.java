package com.common.jdk.desginpattern.adapter;

/**
 * <p>
 *     适配器模式（adapter）包含以下主要角色：
 *      1，目标（Target）接口：当前系统业务所期待的接口，它可以是抽象类或者是接口
 *      2，适配者（adaptee）类：它是被访问和适配的现存组件库中的组件接口。
 *      3，适配器（adapter）类：它是一个转换器，通过继承或者引用适配者的对象，
 *                           把适配者接口转换成目标接口，让客户按目标接口的格式访问适配者。
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 9:31
 */
public class ClassAdapterTest {

    public static void main(String[] args) {
        System.out.println("类适配器模式测试：");
        Target target = new ClassAdapter();
        target.request();
    }
}
