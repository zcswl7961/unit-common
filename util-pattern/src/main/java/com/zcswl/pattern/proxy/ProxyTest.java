package com.zcswl.pattern.proxy;


import java.lang.reflect.*;

/**
 * Created by zhoucg on 2019-03-17.
 *
 * JDK动态代理无法代理静态类
 *
 *
 * $Proxy  extends Proxy implement <接口>
 */
public class ProxyTest {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //设置为true,会在工程根目录生成$Proxy0.class代理类（com.sun.proxy.$Proxy0.class）
        System.getProperties().put(
                "sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        //String saveGeneratedFiles = System.getProperty("sun.misc.ProxyGenerator.saveGeneratedFiles");
        //System.out.println(saveGeneratedFiles);

        HelloTest helloWord = new HelloTestImpl();
        CustomInvocationHandler customInvocationHandler = new CustomInvocationHandler(
                helloWord);
        //通过Proxy.newProxyInstance生成代理对象
        HelloTest proxy = (HelloTest) Proxy.newProxyInstance(
                HelloTest.class.getClassLoader(),
                helloWord.getClass().getInterfaces(), customInvocationHandler);
        //调用say方法
        proxy.say("test");


        Class proxyClass = Proxy.getProxyClass(HelloTest.class.getClassLoader(), HelloTest.class);
        Constructor constructor = proxyClass.getConstructor(InvocationHandler.class);
        HelloTest o = (HelloTest) constructor.newInstance(customInvocationHandler);
        o.say("zhoucg");

        // 改测试实例会指定调用对应的代理对象
        HelloTest helloTest = (HelloTest) Proxy.newProxyInstance(HelloTest.class.getClassLoader(),
                new Class[]{HelloTest.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Class<?> returnType = method.getReturnType();
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        for (int i =0; i < args.length; i++) {
                            if (!args[i].getClass().equals(parameterTypes[i])) {
                                throw new Exception("cuowu");
                            }
                        }
                        // 装饰器返回执行的数据
                        if (int.class.equals(returnType)) {
                            return 1;
                        }
                        if (String.class.equals(returnType)) {
                            return "zhoucg";
                        }
                        return null;
                    }
                });

        int x = helloTest.say("你好");
        System.out.println(x);

        // 判断是否为动态代理对象
        boolean proxyClass1 = Proxy.isProxyClass(helloTest.getClass());
        System.out.println(proxyClass1);

        // 获取对应的InvocationHandler
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(helloTest);
        System.out.println(invocationHandler);

        // 实际上，JDK动态代理对象继承自Proxy，实现了JDK动态代理传入的接口,并实现了接口中的抽象方法
        // 知道这个了，你就应该明白，1，它代理不了静态方法，（因为接口中的静态方法是不会实现的）
        // 2，JDK代理类的接口，不能抽象类
        Proxy helloTestProxy = (Proxy) helloTest;
        System.out.println(helloTestProxy != null);



        // 调用代理类的getInterfaces，getMethod会返回所有接口定义的抽象方法
        Class<?>[] interfaces = helloTest.getClass().getInterfaces();
        Method say = helloTest.getClass().getMethod("say", String.class);
        Object zhoucgwl = say.invoke(helloTest, "zhoucgwl");
        System.out.println(zhoucgwl.equals(1));


        // Proxy的： Methods Duplicated in Multiple Proxy Interfaces




    }
}
