package com.common.util.spi;

/**
 * @author: zhoucg
 * @date: 2019-06-04
 */
public class RedService implements DubboService{
    @Override
    public void sayHello() {
        System.out.println("我是RedService服务实现");
    }
}
