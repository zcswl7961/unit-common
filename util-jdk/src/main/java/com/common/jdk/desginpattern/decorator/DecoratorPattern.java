package com.common.jdk.desginpattern.decorator;

/**
 * <p>
 *      装饰模式主要包含以下角色：
 *          1，抽象构件（Component）角色：定义一个抽象接口以规范准备接收附加责任的对象。
 *          2，具体构件（Concrete    Component）角色：实现抽象构件，通过装饰角色为其添加一些职责。
 *          3，抽象装饰（Decorator）角色：继承抽象构件，并包含具体构件的实例，可以通过其子类扩展具体构件的功能。
 *          4，具体装饰（ConcreteDecorator）角色：实现抽象装饰的相关方法，并给具体构件对象添加附加的责任。
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 10:04
 */
public class DecoratorPattern {

    public static void main(String[] args) {
        Component p=new ConcreteComponent();
        p.operation();
        System.out.println("---------------------------------");
        Component d=new ConcreteDecorator(p);
        d.operation();
    }
}
