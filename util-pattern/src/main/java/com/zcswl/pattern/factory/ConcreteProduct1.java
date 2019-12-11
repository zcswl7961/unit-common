package com.zcswl.pattern.factory;

/**
 * @author zhoucg
 * @date 2019-12-10 15:49
 */
public class ConcreteProduct1 implements Product{
    @Override
    public void show() {
        System.out.println("具体产品1显示...");
    }
}
