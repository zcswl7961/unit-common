package com.common.util.spi;

/**
 * @author: zhoucg
 * @date: 2019-06-04
 */
public class YellowService implements DubboService{
    @Override
    public void sayHello() {
        System.out.println("我是YellowService服务实现");
    }
}
