package com.common.util.spring;

import com.common.util.controller.Property;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * @author: zhoucg
 * @date: 2019-07-28
 */
public class ClassLoaderTest {

    public static void main(String[] args) {


        /*System.out.println(ClassLoaderTest.class.getResource("bean.properties"));
        System.out.println(ClassLoaderTest.class.getResource("/bean.properties"));
        System.out.println(ClassLoaderTest.class.getClassLoader().getResource("bean.properties"));
        System.out.println(ClassLoaderTest.class.getClassLoader().getResource("/bean.properties"));
        System.out.println(ClassLoader.getSystemResource("bean.properties"));*/
//        System.out.println(ClassLoaderTest.class.getResource("/"));
        getProperty("aa");


    }

    private static String getProperty(String name) {
        Properties props = new Properties();
        String file = "/META-INF/MANIFEST.MF";
        URL fileURL = ClassLoaderTest.class.getResource(file);
        if (fileURL != null) {
            try {
                props.load( ClassLoaderTest.class.getResourceAsStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return props.getProperty(name);
    }


}
