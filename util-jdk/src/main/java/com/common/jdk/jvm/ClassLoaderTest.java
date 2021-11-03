package com.common.jdk.jvm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.CompoundEnumeration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.StringTokenizer;

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

        ClassLoader systemClassLoader1 = ClassLoader.getSystemClassLoader();
        ClassLoaderTest.class.getClassLoader();
        // 加载其他的程序
        Class<?> aClass3 = systemClassLoader1.loadClass("com.common.jdk.jvm.SuperClass");

        // 自定义类加载器
        ClassLoader myLoader = new ClassLoaderLocal();
        Class<?> aClass1 = myLoader.loadClass("java.lang.System");

        // return null
        // Class<?> aClass2 = myLoader.loadClass("com.zcswl.error.System");

        // ExtClassLoader 扩展的地址
        String var0 = System.getProperty("java.ext.dirs");
        System.out.println(var0);

        StringTokenizer var2 = new StringTokenizer(var0, File.pathSeparator);
        int i = var2.countTokens();
        System.out.println(i);


        Object obj = myLoader.loadClass("com.common.jdk.jvm.ClassLoaderTest").newInstance();
        ClassLoader classLoader = obj.getClass().getClassLoader();

        // 自定义的ClassLoader
        Class<?> aClass = myLoader.loadClass("java.lang.System");

        System.out.println(obj.getClass());
        System.out.println(obj instanceof ClassLoaderTest);


        // 这个为什么是null? easy to know
        ClassLoader classLoader1 = String.class.getClassLoader();
        //ClassLoader classLoader2 = RSACipher.class.getClassLoader();
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        // Launcher launcher = new Launcher();


        // ClassLoader
        boolean b = ClassLoaderTest.class.getClassLoader() == ClassLoader.getSystemClassLoader();
        System.out.println(b);
    }

    /**
     * 这种写法
     *
     * AccessController.doPrivileged
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

    /**
     * 自定义类加载器
     */
    private static class ClassLoaderLocal extends ClassLoader {

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            try {
                // name ：表示对应的文件路径
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
    }

    /**
     * 继承自对应的UrlClassLoader的类加载器
     * 破坏双亲委派机制的策略类加载器
     */
    private static class LocalClassLoader extends URLClassLoader {

        private static Logger LOGGER = LoggerFactory.getLogger(LocalClassLoader.class);

        private ClassLoader parent;

        private boolean hasExternalRepositories = false;

        public LocalClassLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
            this.parent = parent;
        }

        public LocalClassLoader(URL[] urls) {
            super(urls);
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            return this.loadClass(name, false);
        }

        @Override
        public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            synchronized (getClassLoadingLock(name)) {
                if (LOGGER.isDebugEnabled()){
                    LOGGER.debug("loadClass(" + name + ", " + resolve + ")");
                }
                Class<?> clazz = null;

                // (0.1) Check our previously loaded class cache
                clazz = findLoadedClass(name);
                if (clazz != null) {
                    if (LOGGER.isDebugEnabled()){
                        LOGGER.debug("  Returning class from cache");
                    }
                    if (resolve){
                        resolveClass(clazz);
                    }
                    return (clazz);
                }

                // (2) Search local repositories
                if (LOGGER.isDebugEnabled()){
                    LOGGER.debug("  Searching local repositories");
                }
                try {
                    clazz = findClass(name);
                    if (clazz != null) {
                        if (LOGGER.isDebugEnabled()){
                            LOGGER.debug("  Loading class from local repository");
                        }
                        if (resolve){
                            resolveClass(clazz);
                        }
                        return (clazz);
                    }
                } catch (ClassNotFoundException e) {
                    // Ignore
                }

                if (LOGGER.isDebugEnabled()){
                    LOGGER.debug("  Delegating to parent classloader at end: " + parent);
                }

                try {
                    clazz = Class.forName(name, false, parent);
                    if (clazz != null) {
                        if (LOGGER.isDebugEnabled()){
                            LOGGER.debug("  Loading class from parent");
                        }
                        if (resolve){
                            resolveClass(clazz);
                        }
                        return (clazz);
                    }
                } catch (ClassNotFoundException e) {
                    // Ignore
                }
            }

            throw new ClassNotFoundException(name);
        }

        @Override
        public URL getResource(String name) {

            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("getResource(" + name + ")");
            }

            URL url = null;

            // (2) Search local repositories
            url = findResource(name);
            if (url != null) {
                if (LOGGER.isDebugEnabled()){
                    LOGGER.debug("  --> Returning '" + url.toString() + "'");
                }
                return (url);
            }

            // (3) Delegate to parent unconditionally if not already attempted
            url = parent.getResource(name);
            if (url != null) {
                if (LOGGER.isDebugEnabled()){
                    LOGGER.debug("  --> Returning '" + url.toString() + "'");
                }
                return (url);
            }

            // (4) Resource was not found
            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("  --> Resource not found, returning null");
            }
            return (null);
        }

        @Override
        public void addURL(URL url) {
            super.addURL(url);
            hasExternalRepositories = true;
        }

        /**
         * FIXME 需要测试
         * @param name
         * @return
         * @throws IOException
         */
        @Override
        public Enumeration<URL> getResources(String name) throws IOException {
            @SuppressWarnings("unchecked")
            Enumeration<URL>[] tmp = (Enumeration<URL>[]) new Enumeration<?>[1];
            tmp[0] = findResources(name);//优先使用当前类的资源

            if(!tmp[0].hasMoreElements()){//只有子classLoader找不到任何资源才会调用原生的方法
                return super.getResources(name);
            }

            return new CompoundEnumeration<>(tmp);
        }

        @Override
        public Enumeration<URL> findResources(String name) throws IOException {

            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("findResources(" + name + ")");
            }

            LinkedHashSet<URL> result = new LinkedHashSet<>();

            Enumeration<URL> superResource = super.findResources(name);

            while (superResource.hasMoreElements()){
                result.add(superResource.nextElement());
            }

            // Adding the results of a call to the superclass
            if (hasExternalRepositories) {
                Enumeration<URL> otherResourcePaths = super.findResources(name);
                while (otherResourcePaths.hasMoreElements()) {
                    result.add(otherResourcePaths.nextElement());
                }
            }

            return Collections.enumeration(result);
        }

    }



}
