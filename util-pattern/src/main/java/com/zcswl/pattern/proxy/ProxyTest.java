package com.zcswl.pattern.proxy;

import java.lang.reflect.*;

/**
 * Created by zhoucg on 2019-03-17.
 */
public class ProxyTest {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //设置为true,会在工程根目录生成$Proxy0.class代理类（com.sun.proxy.$Proxy0.class）
        System.getProperties().put(
                "sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        //String saveGeneratedFiles = System.getProperty("sun.misc.ProxyGenerator.saveGeneratedFiles");
        //System.out.println(saveGeneratedFiles);

//        HelloTest helloWord = new HelloTestImpl();
//        CustomInvocationHandler customInvocationHandler = new CustomInvocationHandler(
//                helloWord);
//        //通过Proxy.newProxyInstance生成代理对象
//        HelloTest proxy = (HelloTest) Proxy.newProxyInstance(
//                HelloTest.class.getClassLoader(),
//                helloWord.getClass().getInterfaces(), customInvocationHandler);
//        //调用say方法
//        proxy.say("test");



        /*Class proxyClass = Proxy.getProxyClass(HelloTest.class.getClassLoader(), HelloTest.class);
        Constructor constructor = proxyClass.getConstructor(InvocationHandler.class);
        HelloTest o = (HelloTest) constructor.newInstance(customInvocationHandler);
        o.say("zhoucg");*/


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

    }
}
