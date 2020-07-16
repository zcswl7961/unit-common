package com.zcswl.pattern.builder;


/**
 * @author zhoucg
 * @date 2020-07-11 10:03
 */
public class MacComputerBuilder extends ComputerBuilder {

    private Computer Computer;
    public MacComputerBuilder(String cpu, String ram) {
        Computer = new Computer(cpu, ram);
    }
    @Override
    public void setUsbCount() {
        Computer.setUsbCount(2);
    }
    @Override
    public void setKeyboard() {
        Computer.setKeyboard("苹果键盘");
    }
    @Override
    public void setDisplay() {
        Computer.setDisplay("苹果显示器");
    }
    @Override
    public Computer getComputer() {
        return Computer;
    }
}
