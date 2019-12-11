package com.zcswl.pattern.adapter;

/**
 * @author zhoucg
 * @date 2019-12-10 16:25
 */
public class ClassAdapter extends Adaptee implements  Target{
    @Override
    public void request() {
        specificRequest();
    }
}
