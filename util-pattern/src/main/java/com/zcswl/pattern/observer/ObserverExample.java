package com.zcswl.pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式：指多个对象间存在一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。
 *
 * ApplicationListener
 *
 * ApplicationEvent;
 *
 * @author zhoucg
 * @date 2020-07-13 15:28
 */
public class ObserverExample {


    public static void main(String[] args) {

        Subject subject = new ConcreteSubject();

        Observer obs1 = new ConcreteObserver1();
        Observer obs2 = new ConcreteObserver2();

        subject.add(obs1);
        subject.add(obs2);

        subject.notifyObserver();
    }
}

/**
 * 抽象目标
 */
abstract class Subject {

    protected List<Observer> observers = new ArrayList<Observer>();

    public void add(Observer observer) {
        observers.add(observer);
    }

    public void remove(Observer observer) {
        observers.remove(observer);
    }

    /**
     * 通知观察者方法
     */
    public abstract void notifyObserver();

}

/**
 * 具体目标
 */
class ConcreteSubject extends Subject {

    @Override
    public void notifyObserver() {
        System.out.println("具体目标发生改变...");
        System.out.println("--------------");

        for(Object obs:observers)
        {
            // 此处可以更具具体的测，进行执行指定的观察者
            ((Observer)obs).response();
        }
    }
}


/**
 * 抽象观察者
 */
interface Observer {

    void response();

}

/**
 * 具体观察者1
 */
class ConcreteObserver1 implements Observer {

    @Override
    public void response() {
        System.out.println("具体观察者1作出反应！");
    }
}

class ConcreteObserver2 implements Observer {

    @Override
    public void response() {
        System.out.println("具体观察者2作出反应！");
    }
}


