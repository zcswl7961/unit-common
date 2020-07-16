package com.zcswl.pattern.adapter;

/**
 * 适配器设计模式
 *
 * 适配器的设计模式分为继承类和引用类
 *
 * @author zhoucg
 * @date 2019-12-10 16:25
 */
public class AdaptTest {

    public static void main(String[] args) {
        System.out.println("类适配器测试");
        Target target = new ClassAdapter();

        target.request();
    }
}
