package com.zcswl.pattern.factory;

/**
 * 具体工厂-提供产品2的生产
 * @author zhoucg
 * @date 2019-12-10 15:51
 */
public class ConcreteFactory2 implements AbstractFactory{
    @Override
    public Product newProduct() {
        System.out.println("具体工厂2生成-->具体产品2...");
        return new ConcreteProduct2();
    }
}
