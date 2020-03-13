package com.common.jdk.rmi.registry.facade;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author zhoucg
 * @date 2020-03-04 21:31
 */
public class HelloRegistryFacadeImpl extends UnicastRemoteObject implements HelloRegistryFacade {

    public HelloRegistryFacadeImpl() throws RemoteException {
        super();
    }

    @Override
    public String helloword(String name) throws RemoteException {
        return "[Registry] 你好! " + name;
    }
}
