package com.common.jdk.desginpattern.builder;

/**
 * <p>
 *     展示车
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 9:08
 */
public class CarMain {

    public static void main(String[] args) {
        CarDirector director = new CarDirector();
        director.direct(new CreateCarImpl());

    }

}
