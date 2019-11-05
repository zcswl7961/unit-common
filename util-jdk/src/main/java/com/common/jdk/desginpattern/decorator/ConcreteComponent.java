package com.common.jdk.desginpattern.decorator;

/**
 * <p>
 *     具体构件角色
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 10:00
 */
public class ConcreteComponent implements Component{

    public ConcreteComponent() {
        System.out.println("创建具体构件角色");
    }

    @Override
    public void operation() {
        System.out.println("调用具体构件角色的方法operation()");
    }
}
