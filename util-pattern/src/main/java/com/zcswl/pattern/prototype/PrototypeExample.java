package com.zcswl.pattern.prototype;

/**
 * 原型设计模式
 * @author zhoucg
 * @date 2019-12-10 15:39
 */
public class PrototypeExample implements Cloneable{

    PrototypeExample() {
        System.out.println("具体原型创建成功");
    }

    @Override
    protected PrototypeExample clone() throws CloneNotSupportedException {
        System.out.println("具体的原型复制成功");
        return (PrototypeExample) super.clone();
    }
}
