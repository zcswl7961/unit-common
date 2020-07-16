package com.zcswl.pattern.singleton;

/**
 * @author zhoucg
 * @date 2020-07-11 10:50
 */
public enum SingletonEnumInstance {


    INSTANCE;

    public String getName() {

        System.out.println("zhoucg");
        return "zhoucg";
    }
}
