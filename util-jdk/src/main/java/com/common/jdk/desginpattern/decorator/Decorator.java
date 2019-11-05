package com.common.jdk.desginpattern.decorator;


/**
 * <p>
 *     抽象装饰角色
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 10:01
 */
public class Decorator  implements Component{

    private Component component;
    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void operation() {
        component.operation();
    }
}
