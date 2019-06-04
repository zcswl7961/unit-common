package com.common.util.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author: zhoucg
 * @date: 2019-06-04
 */
public class ServiceMain {

    public static void main(String[] args) {
        ServiceLoader<DubboService> spiLoader = ServiceLoader.load(DubboService.class);
        Iterator<DubboService> iteratorSpi=spiLoader.iterator();
        while (iteratorSpi.hasNext()){
            DubboService dubboService=iteratorSpi.next();
            dubboService.sayHello();
        }
    }
}
