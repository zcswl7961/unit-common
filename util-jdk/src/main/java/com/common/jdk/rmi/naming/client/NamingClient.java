package com.common.jdk.rmi.naming.client;

import com.common.jdk.rmi.naming.facade.Hello;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author zhoucg
 * @date 2020-03-04 21:48
 */
public class NamingClient {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        String remoteAddr = "rmi://localhost:1100/HelloNaming";

        Hello hello = (Hello) Naming.lookup(remoteAddr);

        String response = hello.printMsg();
        System.out.println("=======> " + response + " <=======");
    }
}
