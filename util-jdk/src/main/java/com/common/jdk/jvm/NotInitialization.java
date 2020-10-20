package com.common.jdk.jvm;

/**
 * 类初始化阶段
 *
 * 类加载的时机
 * @author zhoucg
 * @date 2020-10-16 13:35
 */
public class NotInitialization {


    public static void main(String[] args) {
        System.out.println(SubClass.value);

        // 被动使用类
        // 通过数组定义来引用类，不会触发此类的初始化
        SuperClass[] sca = new SuperClass[10];

    }
}
