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

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        switch (x) {
            case 1:
                String s = "0" + x /1;
                break;
            case 2:
                String s = "0" + x /1;
                break;
        }
    }
}


