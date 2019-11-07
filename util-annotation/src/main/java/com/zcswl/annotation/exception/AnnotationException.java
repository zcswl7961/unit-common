package com.zcswl.annotation.exception;

/**
 * 异常类
 * @author zhoucg
 * @date 2019-11-07 10:09
 */
public class AnnotationException extends RuntimeException{

    private Object[] params;

    public AnnotationException() {
        super();
    }

    public AnnotationException(String message) {
        super(message);
    }

    public AnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnnotationException(Throwable cause) {
        super(cause);
    }

    public AnnotationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AnnotationException(String message, String... msgParam) {
        super(message);
        this.params = msgParam;
    }

    public AnnotationException(String message, Object[] params) {
        super(message);
        this.params = params;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
