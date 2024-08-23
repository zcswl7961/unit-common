package com.zcswl.mybatis;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;

/**
 * @author xingyi
 * @date 2024/8/6
 */
@Slf4j
public class InvokeClassLoader extends URLClassLoader {

    private boolean hasExternalRepositories = false;

    /**
     * The parent class loader.
     */
    protected ClassLoader parent;

    public InvokeClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return this.loadClass(name, false);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            if (name.startsWith("com.dtstack.dtcenter.loader.metadata")) {
                return super.loadClass(name, resolve);
            }
            if (log.isDebugEnabled()) {
                log.debug("loadClass(" + name + ", " + resolve + ")");
            }
            Class<?> clazz = null;

            // (0.1) Check our previously loaded class cache
            clazz = findLoadedClass(name);
            if (clazz != null) {
                if (log.isDebugEnabled()) {
                    log.debug("  Returning class from cache");
                }
                if (resolve) {
                    resolveClass(clazz);
                }
                return (clazz);
            }

            // (2) Search local repositories
            if (log.isDebugEnabled()) {
                log.debug("  Searching local repositories");
            }
            try {
                clazz = findClass(name);
                if (clazz != null) {
                    if (log.isDebugEnabled()) {
                        log.debug("  Loading class from local repository");
                    }
                    if (resolve) {
                        resolveClass(clazz);
                    }
                    return (clazz);
                }
            } catch (ClassNotFoundException e) {
                // Ignore
            }


            if (log.isDebugEnabled()) {
                log.debug("  Delegating to parent classloader at end: " + parent);
            }

            try {
                clazz = Class.forName(name, false, parent);
                if (clazz != null) {
                    if (log.isDebugEnabled()) {
                        log.debug("  Loading class from parent");
                    }
                    if (resolve) {
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

        if (log.isDebugEnabled()) {
            log.debug("getResource(" + name + ")");
        }

        URL url = null;

        // (2) Search local repositories
        url = findResource(name);
        if (url != null) {
            if (log.isDebugEnabled()) {
                log.debug("  --> Returning '" + url.toString() + "'");
            }
            return (url);
        }

        // (3) Delegate to parent unconditionally if not already attempted
        url = parent.getResource(name);
        if (url != null) {
            if (log.isDebugEnabled()) {
                log.debug("  --> Returning '" + url.toString() + "'");
            }
            return (url);
        }

        // (4) Resource was not found
        if (log.isDebugEnabled()) {
            log.debug("  --> Resource not found, returning null");
        }
        return (null);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
        hasExternalRepositories = true;
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        Enumeration<URL> resources = findResources(name);
        if (resources.hasMoreElements()) {
            return resources;
        }

        return super.getResources(name);
    }

    @Override
    public Enumeration<URL> findResources(String name) throws IOException {

        if (log.isDebugEnabled()) {
            log.debug("findResources(" + name + ")");
        }

        LinkedHashSet<URL> result = new LinkedHashSet<>();

        Enumeration<URL> superResource = super.findResources(name);

        while (superResource.hasMoreElements()) {
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
