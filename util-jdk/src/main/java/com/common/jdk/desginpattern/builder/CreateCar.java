package com.common.jdk.desginpattern.builder;

/**
 * <p>
 *     造车接口
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 9:04
 */
public interface CreateCar {

    void selectColor();

    void selectCarDoor();

    void selectTires();

    Car createCar();
}
