package com.zcswl.pattern.decorator;

/**
 * @author zhoucg
 * @date 2020-07-11 13:42
 */
public class Man implements Human{
    @Override
    public void run() {
        System.out.println("人可以跑步");
    }
}
