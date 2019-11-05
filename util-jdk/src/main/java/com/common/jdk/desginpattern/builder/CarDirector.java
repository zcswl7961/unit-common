package com.common.jdk.desginpattern.builder;

/**
 * <p>
 *     描述车的过程
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 9:08
 */
public class CarDirector {

    public CreateCar direct(CreateCar createCar){

        createCar.selectCarDoor();
        createCar.selectColor();
        createCar.selectTires();
        return createCar;
    }
}
