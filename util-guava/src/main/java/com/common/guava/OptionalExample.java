package com.common.guava;

import com.google.common.base.Optional;

/**
 * @author: zhoucg
 * @date: 2019-10-12
 */
public class OptionalExample {

    public static void main(String[] args) {
        Optional of = of(null);
    }


    /**
     * 接收一个obj对象
     * @param obj
     * @return
     */
    public static Optional of(Object obj) {
        return Optional.of(obj);
    }



}
