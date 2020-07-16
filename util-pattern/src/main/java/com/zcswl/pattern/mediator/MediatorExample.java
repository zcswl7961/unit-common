package com.zcswl.pattern.mediator;

/**
 * 中介者模式：定义一个中介对象来封装一系列对象之间的交互，使原有对象之间的耦合松散，
 * 且可以独立地改变它们之间的交互。中介者模式又叫调停模式，它是迪米特法则的典型应用。
 *
 * @author zhoucg
 * @date 2020-07-13 16:35
 */
public class MediatorExample {


    public static void main(String[] args) {

    }
}


/**
 * 抽象中介者
 */
abstract class Mediator {

    public abstract void register();
    public abstract void relay();

}


/**
 * 抽象同事类
 */
abstract class Colleague {

    protected Mediator mediator;
    public void setMedium(Mediator mediator)
    {
        this.mediator=mediator;
    }


    public abstract void receive();
    public abstract void send();

}

