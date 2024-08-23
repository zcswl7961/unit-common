package com.dtstack.test;

/**
 * @author xingyi
 * @date 2023/11/8
 */
public class Main {

    public String test(String input) {
        if (input == null || input.length() == 0) {
            return "Hello world";
        }
        return "Hello world " + input;
    }
}
