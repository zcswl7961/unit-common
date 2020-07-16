package com.zcswl.pattern.builder;

/**
 * @author zhoucg
 * @date 2020-07-11 10:11
 */
public class ComputerDirector {

    public void makeComputer(ComputerBuilder builder){
        builder.setUsbCount();
        builder.setDisplay();
        builder.setKeyboard();
    }
}
