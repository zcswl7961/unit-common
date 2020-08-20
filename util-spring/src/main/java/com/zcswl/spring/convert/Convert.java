package com.zcswl.spring.convert;

/**
 * @see org.springframework.core.convert.converter.Converter
 *
 * @author zhoucg
 * @date 2020-07-16 17:16
 */
@FunctionalInterface
public interface Convert<S, T> {

    T convert(S source);
}
