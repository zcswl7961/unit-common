package com.common.jdk.rmi.naming.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author zhoucg
 * @date 2020-03-05 10:18
 */
public interface Hello extends Remote{

    String printMsg() throws RemoteException;
}
