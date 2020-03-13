package com.common.jdk.rmi.registry.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author zhoucg
 * @date 2020-03-04 21:30
 */
public interface HelloRegistryFacade extends Remote{


    String helloword(String name) throws RemoteException;
}
