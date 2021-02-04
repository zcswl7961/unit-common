package com.common.jdk.spi;

import java.util.ServiceLoader;

/**
 *
 * jdk原生提供的SPI机制
 *
 * 1, 定义接口
 * 2, 定义SPI对应的文档 META-INFO/services/xxx.接口名（以接口的全限定名作为文件的名称）
 * 3, 定义实现接口的实现类的全限定名
 *
 * @author zhoucg
 * @date 2021-02-02 13:38
 */
public class ServiceLoaderDemo {


    public static void main(String[] args) {
        ServiceLoader<Service> load = ServiceLoader.load(Service.class);
        for (Service next : load) {
            String say = next.say();
            System.out.println(say);
        }
    }
}
