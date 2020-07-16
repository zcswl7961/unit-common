package com.zcswl.pattern.decorator;

/**
 * 装饰者设计模式
 *
 * @author zhoucg
 * @date 2020-07-12 12:08
 */
public class DecoratorTest {

    public static void main(String[] args) {


        // 原始的操作
        Component component = new ConcreteComponent();
        component.operator();

        // 装饰了第一个
        Component component1 = new ConcreteDecorator1(component);
        component1.operator();

        // 装饰了第一个和第二个
        Component component2 = new ConcreteDecorator1(new ConcreteDecorator2(component));
        component2.operator();
    }
}

/**
 * 定义了一个接口
 */
interface Component {
    void operator();
}

/**
 * 实现
 */
class ConcreteComponent implements Component {

    @Override
    public void operator() {
        System.out.println("拍照功能");
    }
}

/**
 * 增加一个装饰器
 */
abstract class Decorator implements Component {

    Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    /**
     * 同时，你也可以把对应的抽象方法定义到这里
     */
    @Override
    public void operator() {
        // 执行原始的抽象方法，
        component.operator();
        deepDecorator();
    }

    /**
     * 定义对应的抽象方法，交给子类进行处理，并对原始的方法进行丰富
     */
    public abstract void deepDecorator();
}

/**
 * 第一个装饰器
 */
class ConcreteDecorator1 extends Decorator{


    public ConcreteDecorator1(Component component) {
        super(component);
    }


    @Override
    public void deepDecorator() {
        System.out.println("拍照完之后，增加一个美颜的功能");
    }
}

/**
 * 第二个装饰器
 */
class ConcreteDecorator2 extends Decorator {


    public ConcreteDecorator2(Component component) {
        super(component);
    }

    @Override
    public void deepDecorator() {
        System.out.println("增加一个滤镜的功能");
    }
}

