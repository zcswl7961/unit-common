package com.zcswl.pattern.builder.old;

/**
 * 建造者设计模式
 *
 * @author zhoucg
 * @date 2020-07-11 9:55
 */
public class ComputerBuilder {

    private final String cpu;
    private final String ram;
    private final int usbCount;//可选
    private final String keyboard;//可选
    private final String display;//可选

    private ComputerBuilder(Builder builder){
        this.cpu=builder.cpu;
        this.ram=builder.ram;
        this.usbCount=builder.usbCount;
        this.keyboard=builder.keyboard;
        this.display=builder.display;
    }

    public static class Builder{
        private String cpu;//必须
        private String ram;//必须
        private int usbCount;//可选
        private String keyboard;//可选
        private String display;//可选

        public Builder(String cup,String ram){
            this.cpu=cup;
            this.ram=ram;
        }

        public Builder setUsbCount(int usbCount) {
            this.usbCount = usbCount;
            return this;
        }
        public Builder setKeyboard(String keyboard) {
            this.keyboard = keyboard;
            return this;
        }
        public Builder setDisplay(String display) {
            this.display = display;
            return this;
        }
        public ComputerBuilder build(){
            return new ComputerBuilder(this);
        }
    }
}
