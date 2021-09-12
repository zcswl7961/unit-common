package com.zcswl.google.guice;

import com.google.inject.Inject;

/**
 * @author xingyi
 * @date 2021/9/11
 */
public class DogEgg {

    @Inject
    public Service service;

    @Inject
    @DefaultAnnotation
    public Service service2;

    public String say() {
        service.guiceHello();
        return "1";
    }
}
