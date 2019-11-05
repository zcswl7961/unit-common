package com.zcswl.spi;

import com.zcswl.inter.Pay;

import javax.annotation.processing.Processor;
import java.util.ServiceLoader;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-04 15:54
 */
public class SpiTest {

    public static void main(String[] args) {
        ServiceLoader<Pay> load = ServiceLoader.load(Pay.class);
        for(Pay pay : load) {
            pay.pay();
        }

        ServiceLoader<Processor> load1 = ServiceLoader.load(Processor.class);
        for(Processor processor : load1) {
            System.out.println(processor);
        }
    }
}
