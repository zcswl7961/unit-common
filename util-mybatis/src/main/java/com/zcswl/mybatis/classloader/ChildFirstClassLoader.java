package com.zcswl.mybatis.classloader;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * A variant of the URLClassLoader that first loads from the URLs and only after that from the
 * parent.
 *
 * <p>{@link #getResourceAsStream(String)} uses {@link #getResource(String)} internally so we don't
 * override that.
 * @author xingyi
 * @date 2024/8/7
 */
public class ChildFirstClassLoader extends URLClassLoader {

    static {
        ClassLoader.registerAsParallelCapable();
    }

    public static final Consumer<Throwable> NOOP_EXCEPTION_HANDLER = classLoadingException -> {};

    private final Consumer<Throwable> classLoadingExceptionHandler;

    private final List<String> alwaysChildFirstClasses;

    public ChildFirstClassLoader(URL[] urls, ClassLoader parent, List<String> alwaysChildFirstPatterns) {
        this(urls, parent, NOOP_EXCEPTION_HANDLER, alwaysChildFirstPatterns);

    }

    protected ChildFirstClassLoader(
            URL[] urls, ClassLoader parent, Consumer<Throwable> classLoadingExceptionHandler,
            List<String> alwaysChildFirstClasses) {
        super(urls, parent);
        this.classLoadingExceptionHandler = classLoadingExceptionHandler;
        this.alwaysChildFirstClasses = alwaysChildFirstClasses == null ? new ArrayList<>() : alwaysChildFirstClasses;
    }

    @Override
    public final Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        try {
            synchronized (getClassLoadingLock(name)) {
                return loadClassWithoutExceptionHandling(name, resolve);
            }
        } catch (Throwable classLoadingException) {
            classLoadingExceptionHandler.accept(classLoadingException);
            throw classLoadingException;
        }
    }

    /**
     * Same as {@link #loadClass(String, boolean)} but without exception handling.
     *
     * <p>Extending concrete class loaders should implement this instead of {@link
     * #loadClass(String, boolean)}.
     */
    protected Class<?> loadClassParentWithoutExceptionHandling(String name, boolean resolve)
            throws ClassNotFoundException {
        return super.loadClass(name, resolve);
    }

    protected Class<?> loadClassWithoutExceptionHandling(String name, boolean resolve)
            throws ClassNotFoundException {

        // First, check if the class has already been loaded
        Class<?> c = findLoadedClass(name);

        if (c == null) {
            // check whether the class should go parent-first

            boolean ifContains = alwaysChildFirstClasses.stream().anyMatch(name::equals);
            if (!ifContains) {
                return this.loadClassParentWithoutExceptionHandling(name, resolve);
            }

            try {
                // check the URLs
                c = findClass(name);
            } catch (ClassNotFoundException e) {
                // let URLClassLoader do it, which will eventually call the parent
                c = this.loadClassParentWithoutExceptionHandling(name, resolve);
            }
        } else if (resolve) {
            resolveClass(c);
        }

        return c;
    }

    @Override
    public URL getResource(String name) {
        // first, try and find it via the URLClassloader
        URL urlClassLoaderResource = findResource(name);

        if (urlClassLoaderResource != null) {
            return urlClassLoaderResource;
        }

        // delegate to super
        return super.getResource(name);
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        // first get resources from URLClassloader
        Enumeration<URL> urlClassLoaderResources = findResources(name);

        final List<URL> result = new ArrayList<>();

        while (urlClassLoaderResources.hasMoreElements()) {
            result.add(urlClassLoaderResources.nextElement());
        }

        // get parent urls
        Enumeration<URL> parentResources = getParent().getResources(name);

        while (parentResources.hasMoreElements()) {
            result.add(parentResources.nextElement());
        }

        return new Enumeration<URL>() {
            Iterator<URL> iter = result.iterator();

            public boolean hasMoreElements() {
                return iter.hasNext();
            }

            public URL nextElement() {
                return iter.next();
            }
        };
    }

}
