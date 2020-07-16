package com.zcswl.pattern.singleton;



/**
 * @author zhoucg
 * @date 2020-07-11 10:51
 */
public class Test {


    public static void main(String[] args) {


        // 懒汉的设计模式
        SingletonExample singletonExample = SingletonExample.getInstance();

        // 饿汉的设计模式
        SingletonExample singletonExample1 = SingletonExample.getInstanceHungry();

        // 使用枚举创建单例模式
        SingletonEnumInstance singletonEnumInstance = SingletonEnumInstance.INSTANCE;
        singletonEnumInstance.getName();
    }
}
