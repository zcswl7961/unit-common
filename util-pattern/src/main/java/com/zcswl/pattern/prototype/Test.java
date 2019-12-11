package com.zcswl.pattern.prototype;

/**
 * @author zhoucg
 * @date 2019-12-10 15:43
 */
public class Test {

    public static void main(String[] args) throws CloneNotSupportedException {
        PrototypeExample prototypeExample = new PrototypeExample();
        PrototypeExample clonePrototypeExample = prototypeExample.clone();
        System.out.println(prototypeExample == clonePrototypeExample);
    }
}
