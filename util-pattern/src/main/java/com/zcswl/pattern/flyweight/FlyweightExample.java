package com.zcswl.pattern.flyweight;

import java.util.HashMap;

/**
 * 享元模式
 *
 * @author zhoucg
 * @date 2020-07-13 10:50
 */
public class FlyweightExample {

    public static void main(String[] args) {


        FlyweightFactory factory=new FlyweightFactory();
        Flyweight f01=factory.getFlyweight("a");
        Flyweight f02=factory.getFlyweight("a");
        Flyweight f03=factory.getFlyweight("a");
        Flyweight f11=factory.getFlyweight("b");
        Flyweight f12=factory.getFlyweight("b");
        f01.operation(new UnsharedConcreteFlyweight("第1次调用a。"));
        f02.operation(new UnsharedConcreteFlyweight("第2次调用a。"));
        f03.operation(new UnsharedConcreteFlyweight("第3次调用a。"));
        f11.operation(new UnsharedConcreteFlyweight("第1次调用b。"));
        f12.operation(new UnsharedConcreteFlyweight("第2次调用b。"));
    }
}


/**
 * 非享元角色：是不可以共享的外部状态，它以参数的形式注入具体享元的相关方法中。
 */
class UnsharedConcreteFlyweight {
    private String info;

    UnsharedConcreteFlyweight(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

/**
 * 抽象享元基类：是所有的具体享元类的基类，为具体享元规范需要实现的公共接口，非享元的外部状态以参数的形式通过方法传入。
 */
interface Flyweight {

    public void operation(UnsharedConcreteFlyweight  state);
}

/**
 * 具体享元（Concrete Flyweight）角色：实现抽象享元角色中所规定的接口。
 */
class ConcreteFlyweight implements Flyweight {

    private String key;

    ConcreteFlyweight(String key) {
        this.key = key;
        System.out.println("具体享元"+key+"被创建！");
    }


    @Override
    public void operation(UnsharedConcreteFlyweight outState) {
        System.out.print("具体享元"+key+"被调用，");
        System.out.println("非享元信息是:"+outState.getInfo());
    }
}

/**
 * 享元工厂（Flyweight Factory）角色：负责创建和管理享元角色。当客户对象请求一个享元对象时，
 * 享元工厂检査系统中是否存在符合要求的享元对象，如果存在则提供给客户；如果不存在的话，则创建一个新的享元对象。
 */
class FlyweightFactory {


    private HashMap<String, Flyweight> flyweights=new HashMap<String, Flyweight>();
    public Flyweight getFlyweight(String key) {
        Flyweight flyweight=(Flyweight)flyweights.get(key);
        if(flyweight!=null)
        {
            System.out.println("具体享元"+key+"已经存在，被成功获取！");
        }
        else
        {
            flyweight=new ConcreteFlyweight(key);
            flyweights.put(key, flyweight);
        }
        return flyweight;
    }

}