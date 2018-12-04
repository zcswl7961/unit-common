package com.common.util;

/**
 * Created by zhoucg on 2018-11-20.
 */
public class NumberTest {

    public static void main(String[] args) {
        String number1 = "3.2400352E5";
        number1 = number1.substring(0,number1.indexOf("."));
        System.out.println("number1="+number1);
    }
}
