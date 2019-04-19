package com.common.util.proxy.staticProxy;

/**
 * Created by zhoucg on 2019-04-18.
 * 代理模式最主要的就是有一个公共接口（Person）,
 * 一个具体的类（Student），一个代理类（StudentsProxy）,代理类持有具体类的实例
 * 代为执行具体类实例方法。上面说到，代理模式就是在访问实际对象时引入一定程度的间接性，
 * 因为这种间接性，可以附加多种用途。这里的间接性就是指不直接调用实际对象的方法，
 * 那么我们在代理过程中就可以加上一些其他用途。就这个例子来说，
 * 加入班长在帮张三上交班费之前想要先反映一下张三最近学习有很大进步，通过代理模式很轻松就能办到：
 */
public class StaticProxyTest {

    public static void main(String[] args) {
        Person zhangsan  = new Student("张三");

        //生成代理对象，并将张三传给代理对象
        Person monitor = new StudentsProxy(zhangsan);

        //班长代理上交班费
        monitor.giveMoney();


    }
}
