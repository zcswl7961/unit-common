package com.common.util.enums;

/**
 * @author: zhoucg
 * @date: 2019-09-19
 */
public class EnumTest {

    public static void main(String[] args) {
        Enum[] values = Enum.values();
        for(Enum value :values) {
            String description = value.getDescription();
            System.out.println(description);
        }
    }
}
