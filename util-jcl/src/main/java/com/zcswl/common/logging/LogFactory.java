package com.zcswl.common.logging;


/**
 * spring-jcl LogFactory
 * @author zhoucg
 * @date 2020-01-06 17:13
 */
public abstract class LogFactory {


    /**
     * Convenience method to return a named logger.
     * @param clazz containing Class from which a log name will be derived
     */
    public static Log getLog(Class<?> clazz) {
        return getLog(clazz.getName());
    }

    /**
     * Convenience method to return a named logger.
     * @param name logical name of the <code>Log</code> instance to be returned
     */
    public static Log getLog(String name) {
        return LogDelegate.createLog(name);
    }


    /**
     * This method only exists for compatibility with unusual Commons Logging API
     * usage like e.g. {@code LogFactory.getFactory().getInstance(Class/String)}.
     * @see #getInstance(Class)
     * @see #getInstance(String)
     * @deprecated in favor of {@link #getLog(Class)}/{@link #getLog(String)}
     */
    @Deprecated
    public static LogFactory getFactory() {
        return new LogFactory() {};
    }

    /**
     * Convenience method to return a named logger.
     * <p>This variant just dispatches straight to {@link #getLog(Class)}.
     * @param clazz containing Class from which a log name will be derived
     * @deprecated in favor of {@link #getLog(Class)}
     */
    @Deprecated
    public Log getInstance(Class<?> clazz) {
        return getLog(clazz);
    }

    /**
     * Convenience method to return a named logger.
     * <p>This variant just dispatches straight to {@link #getLog(String)}.
     * @param name logical name of the <code>Log</code> instance to be returned
     * @deprecated in favor of {@link #getLog(String)}
     */
    @Deprecated
    public Log getInstance(String name) {
        return getLog(name);
    }
}
