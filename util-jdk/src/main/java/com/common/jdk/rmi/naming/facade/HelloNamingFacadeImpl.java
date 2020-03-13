package com.common.jdk.rmi.naming.facade;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author zhoucg
 * @date 2020-03-04 21:31
 */
public class HelloNamingFacadeImpl extends UnicastRemoteObject implements HelloNamingFacade {

    public HelloNamingFacadeImpl() throws RemoteException {
        super();
    }

    @Override
    public String helloword(String name) throws RemoteException {
        return "[Registry] 你好! " + name;
    }
}
