package com.common.jdk.desginpattern.decorator;

/**
 * <p>
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 10:02
 */
public class ConcreteDecorator extends Decorator{


    public ConcreteDecorator(Component component) {
        super(component);
    }

    @Override
    public void operation() {
        super.operation();
        addedFunction();
    }

    public void addedFunction()
    {
        System.out.println("为具体构件角色增加额外的功能addedFunction()");
    }
}
