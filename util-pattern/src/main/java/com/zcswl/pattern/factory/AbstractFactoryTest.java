package com.zcswl.pattern.factory;

/**
 * @author zhoucg
 * @date 2019-12-10 15:59
 */
public class AbstractFactoryTest {

    public static void main(String[] args) {
        Product a;
        AbstractFactory af;
        af=(AbstractFactory) XmlReader.getObject();
        a=af.newProduct();
        a.show();
    }
}
