package com.common.jdk.rmi.registry.server;


import com.common.jdk.rmi.registry.facade.HelloRegistryFacade;
import com.common.jdk.rmi.registry.facade.HelloRegistryFacadeImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * rmi和jndi之间的关系
 * https://blog.csdn.net/u011479200/article/details/108246846
 * @author zhoucg
 * @date 2020-03-04 21:28
 */
public class RegistryService {


    public static void main(String[] args) throws RemoteException {

        // 本地主机上的远程对象注册表Registry的实例,默认端口1099
        Registry registry = LocateRegistry.createRegistry(1099);

        // 创建一个远程对象
        HelloRegistryFacade hello = new HelloRegistryFacadeImpl();
        // 把远程对象注册到RMI注册服务器上，并命名为HelloRegistry
        registry.rebind("HelloRegistry", hello);

        System.out.println("======= 启动RMI服务成功! =======");

    }
}


