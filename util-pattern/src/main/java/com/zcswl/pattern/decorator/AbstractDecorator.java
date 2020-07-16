package com.zcswl.pattern.decorator;

/**
 * @author zhoucg
 * @date 2020-07-11 13:43
 */
public abstract class AbstractDecorator implements Human{


    private Human human;

    public AbstractDecorator(Human human) {
        this.human = human;
    }


    @Override
    public void run() {
        human.run();
        fly();

    }


    private void fly() {
        System.out.println("人可以分（装饰）");
    }
}
