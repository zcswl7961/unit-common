package com.common.jdk.spi;

/**
 * @author zhoucg
 * @date 2021-02-02 13:41
 */
public class ServiceImpl1 implements Service{
    @Override
    public String say() {
        return "ServiceImpl1";
    }
}
