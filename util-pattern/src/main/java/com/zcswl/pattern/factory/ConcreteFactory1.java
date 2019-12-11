package com.zcswl.pattern.factory;


/**
 * 具体工厂生产产品1
 * @author zhoucg
 * @date 2019-12-10 15:51
 */
public class ConcreteFactory1 implements AbstractFactory{
    @Override
    public Product newProduct() {
        System.out.println("具体工厂1生成-->具体产品1...");
        return new ConcreteProduct1();
    }
}
