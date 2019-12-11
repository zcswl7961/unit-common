package com.zcswl.pattern.singleton;



/**
 * 单例设计模式
 * @author zhoucg
 * @date 2019-12-10 15:33
 */
public class SingletonExample {

    private static volatile SingletonExample  instance = null;

    private SingletonExample() {}

    /**
     * 懒汉模式下的单例设计模式，需要使用volatile，synchroniezd 锁机制控制多线程问题
     * @return
     */
    public static SingletonExample getInstance() {
        if(instance == null) {
            synchronized (SingletonExample.class) {
                if(instance == null) {
                    instance = new SingletonExample();
                }
            }
        }
        return instance;
    }

    private static final SingletonExample hungryInstance = new SingletonExample();

    public static SingletonExample getInstanceHungry() {
        return hungryInstance;
    }


}
