package com.common.jdk.desginpattern.adapter;


/**
 * <p>
 *     类适配器类
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 9:30
 */
public class ClassAdapter extends Adaptee implements Target{
    @Override
    public void request() {
        specificRequest();
    }
}
