package com.common.jdk.rmi.registry.client;

import com.common.jdk.rmi.registry.facade.HelloRegistryFacade;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author zhoucg
 * @date 2020-03-04 21:33
 */
public class RegistryClient {


    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(1099);
        HelloRegistryFacade hello = (HelloRegistryFacade) registry.lookup("HelloRegistry");
        String response = hello.helloword("zhoucg");
        System.out.println("=======> " + response + " <=======");
    }
}
