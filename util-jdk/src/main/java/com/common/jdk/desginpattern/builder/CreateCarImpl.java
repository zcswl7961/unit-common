package com.common.jdk.desginpattern.builder;

/**
 * <p>
 *     实现造车的功能
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 9:05
 */
public class CreateCarImpl implements CreateCar{

    Car car;

    public CreateCarImpl(){
        car = new Car();
    }

    @Override
    public void selectColor() {
        car.setColor("颜色");
    }

    @Override
    public void selectCarDoor() {
        car.setCarDoor(4);
    }

    @Override
    public void selectTires() {
        car.setTires(3);
    }

    @Override
    public Car createCar() {
        return car;
    }
}
