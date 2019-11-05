package com.common.jdk.desginpattern.bridge;

/**
 * <p>
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 9:42
 */
public class RefinedAbstraction extends Abstraction {

    protected RefinedAbstraction(Implementor imple)
    {
        super(imple);
    }

    @Override
    public void Operation() {
        System.out.println("扩展抽象化(Refined Abstraction)角色被访问" );
        imple.OperationImpl();
    }
}
