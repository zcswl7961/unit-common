package com.common.jdk.rmi.naming.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author zhoucg
 * @date 2020-03-04 21:30
 */
public interface HelloNamingFacade extends Remote{


    String helloword(String name) throws RemoteException;
}
