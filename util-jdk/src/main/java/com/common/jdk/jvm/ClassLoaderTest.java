package com.common.jdk.jvm;

import sun.security.mscapi.RSACipher;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * 两行输出结果中，从第一行可以看到这个对象确实是类org.fenixsoft.classloading.ClassLoaderTest实
 * 例化出来的
 * 但在第二行的输出中却发现这个对象与类org.fenixsoft.classloading.ClassLoaderTest做所属
 * 类型检查的时候返回了false。这是因为Java虚拟机中同时存在了两个ClassLoaderTest类，
 * 一个是由虚拟
 * 机的应用程序类加载器所加载的，另外一个是由我们自定义的类加载器加载的，虽然它们都来自同一
 * 个Class文件，但在Java虚拟机中仍然是两个互相独立的类，做对象所属类型检查时的结果自然为
 * false。
 *
 * 深入理解jvm虚拟机 类加载器
 * @author zhoucg
 * @date 2020-10-16 15:24
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {

                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream inputStream = getClass().getResourceAsStream(fileName);
                    if (inputStream == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[inputStream.available()];
                    inputStream.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = myLoader.loadClass("com.common.jdk.jvm.ClassLoaderTest").newInstance();
        ClassLoader classLoader = obj.getClass().getClassLoader();

        System.out.println(obj.getClass());
        System.out.println(obj instanceof ClassLoaderTest);


        // 这个为什么是null
        ClassLoader classLoader1 = String.class.getClassLoader();
        ClassLoader classLoader2 = RSACipher.class.getClassLoader();
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
    }

    /**
     * 这种写法
     */
    ClassLoader getContextClassLoader() {
        return (ClassLoader)
                AccessController.doPrivileged(new PrivilegedAction() {
                    public Object run() {
                        ClassLoader cl = null;
                        try {
                            cl = Thread.currentThread().getContextClassLoader();
                        } catch (SecurityException ex) { }
                        return cl;
                    }
                });
    }
}
