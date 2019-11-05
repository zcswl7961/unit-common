package com.common.jdk.desginpattern.bridge;

/**
 * <p>
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 9:41
 */
public class ConcreteImplementorA implements Implementor{
    @Override
    public void OperationImpl() {
        System.out.println("具体实现化(Concrete Implementor)角色被访问" );
    }
}
