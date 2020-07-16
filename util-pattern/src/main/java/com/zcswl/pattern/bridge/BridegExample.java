package com.zcswl.pattern.bridge;

/**
 * 桥接模式
 *
 * @author zhoucg
 * @date 2020-07-13 10:29
 */
public class BridegExample {


    public static void main(String[] args) {

    }
}



/**
 * 实现化角色
 */
abstract class Implementor {

    /**
     * 示例方法，实现抽象部分需要的某些具体功能
     */
    public abstract void operatorImpl();
}

/**
 * 具体实现化1
 */
class ConcreteImplementorA extends Implementor {


    @Override
    public void operatorImpl() {
        //
        System.out.println("具体实现化1");
    }
}

class ConcreteImplementorB extends Implementor {

    @Override
    public void operatorImpl() {
        //
        System.out.println("具体实现化2");
    }
}



/**
 * 抽象化（Abstraction）角色：抽象化给出的定义，并保存一个对实现化对象的引用
 */
abstract class Abstraction {

    protected Implementor impl;

    public Abstraction(Implementor impl){
        this.impl = impl;
    }

    public void operator() {
        impl.operatorImpl();
    }

}


/**
 * 具体抽象化
 */
class RefinedAbstraction extends Abstraction {

    public RefinedAbstraction(Implementor impl) {
        super(impl);
    }

    public void otherOperation() {
        // 其他操作
    }

}
