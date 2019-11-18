package com.zcswl.db.util;

/**
 * @author zhoucg
 * @date 2019-11-15 17:40
 */
public class BeanUtilException extends Throwable {

    /**
     * <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -2857599938139049801L;

    /**
     * Catches exceptions without a specified string
     *
     */
    public BeanUtilException() {
    }

    /**
     * Constructs the appropriate exception with the specified string
     *
     * @param message
     *          String Exception message
     */
    public BeanUtilException(String message) {
        super(message);
    }

    public BeanUtilException(String message,Throwable cause) {
        super(message,cause);
    }

    public BeanUtilException(Throwable cause) {
        super(cause);
    }
}
