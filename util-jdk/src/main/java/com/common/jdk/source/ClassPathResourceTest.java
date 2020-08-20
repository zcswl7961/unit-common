package com.common.jdk.source;

import java.net.URL;

/**
 * spring： ClassPathResource
 *
 * https://blog.csdn.net/zcswl7961/article/details/103831231
 *
 * @author zhoucg
 * @date 2020-07-16 15:23
 */
public class ClassPathResourceTest {

    public static void main(String[] args) {


        // Class 和 ClassLoader 中的getResource 和 getResourceAsStream() 测试

        // 1, 通过当前类的字节码文件获取ClassLoader  Class.getClassLoader()
        ClassLoader classLoaderFromClass = ClassPathResourceTest.class.getClassLoader();
        // 2, 通过ClassLoader静态方法getSystemClassLoader() 获取类加载器
        ClassLoader classLoaderFromStatic = ClassLoader.getSystemClassLoader();
        // true
        System.out.println(classLoaderFromClass == classLoaderFromStatic);


        /**
         * ClassLoader.getResource(String name) 只能够从classpath根目录开始匹配获取资源，
         *                      写法  Class.getResource(“test.properties”)
         * Class.gerResource(String name) 可以从当前Class所在包的路径开始匹配资源,
         *                              写法;Class.getResource("")
         *                      也可以从classpath根路径开始获取资源，
         *                              写法：Class.getResource("/")
         */
        // 通过ClassLoader.getResource 获取资源数据
        URL resource = classLoaderFromClass.getResource("resources-path.properties");
        URL resource1 = classLoaderFromClass.getResource("com/common/jdk/source/current-path.properties");


        // ClassLoader.gerResource 不能以"/" 开头，且路径总是从classpath跟目录开始
        // classLoaderFromClass.getResource("/resources-path.properties"); // 报错

        // 通过Class.getResource 获取对应classpath跟目录的文件 ,以 "/" 开头从当前跟目录获取
        URL resource2 = ClassPathResourceTest.class.getResource("/resources-path.properties");
        // 通过Class.getResource 获取对应的当前class 目录对应的数据信息
        URL resource3 = ClassPathResourceTest.class.getResource("current-path.properties");
    }
}
