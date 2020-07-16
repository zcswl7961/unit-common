package com.zcswl.pattern.builder;

/**
 * @author zhoucg
 * @date 2020-07-11 10:02
 */
public abstract class ComputerBuilder {

    public abstract void setUsbCount();
    public abstract void setKeyboard();
    public abstract void setDisplay();

    public abstract Computer getComputer();
}
