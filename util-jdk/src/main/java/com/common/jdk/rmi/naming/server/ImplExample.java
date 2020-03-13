package com.common.jdk.rmi.naming.server;

import com.common.jdk.rmi.naming.facade.Hello;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author zhoucg
 * @date 2020-03-05 10:19
 */
public class ImplExample extends UnicastRemoteObject implements Hello{

    public ImplExample() throws RemoteException {
        super();
    }

    @Override
    public String printMsg() throws RemoteException {
        System.out.println("This is an example RMI program");
        return "ZHOUCG";
    }
}
