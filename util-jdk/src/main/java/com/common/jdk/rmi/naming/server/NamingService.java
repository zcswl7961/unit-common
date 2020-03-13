package com.common.jdk.rmi.naming.server;

import com.common.jdk.rmi.naming.facade.Hello;
import com.common.jdk.rmi.naming.facade.HelloNamingFacade;
import com.common.jdk.rmi.naming.facade.HelloNamingFacadeImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 *
 * 使用Naming方法实现rmi
 *
 * @author zhoucg
 * @date 2020-03-04 21:38
 */
public class NamingService {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        // 本地主机上的远程对象注册表Registry的实例
        LocateRegistry.createRegistry(1100);

        // 创建一个远程对象
        Hello hello = new ImplExample();

        // 把远程对象注册到RMI注册服务器上，并命名为Hello
        // 绑定的URL标准格式为：rmi://host:port/name
        Naming.bind("rmi://localhost:1100/HelloNaming",hello);

        System.out.println("======= 启动RMI服务成功! =======");

    }
}
