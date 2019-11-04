package com.zcswl.tools.spi;

import java.util.ServiceLoader;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-04 15:26
 */
public class SpiExample {

    public static void main(String[] args) {


        ServiceLoader<Pay> load = ServiceLoader.load(Pay.class);
        for (Pay pay : load) {
            pay.pay();
        }
    }
}
