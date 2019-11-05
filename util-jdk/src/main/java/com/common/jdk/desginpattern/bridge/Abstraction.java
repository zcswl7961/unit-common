package com.common.jdk.desginpattern.bridge;

/**
 * <p>
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 9:41
 */
public abstract class Abstraction {

    protected Implementor imple;
    protected Abstraction(Implementor imple)
    {
        this.imple=imple;
    }

    public abstract void Operation();
}
