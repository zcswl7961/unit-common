package com.zcswl.google.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author xingyi
 * @date 2021/9/11
 */
public class Test {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BindModule());
        DogEgg dogEgg = injector.getInstance(DogEgg.class);
        System.out.println(dogEgg.service);
        System.out.println(dogEgg.service2);
    }
}
