package com.common.util.lambda;

import java.util.function.Supplier;

/**
 * Created by zhoucg on 2019-04-16.
 *
 * Supplier function
 * supplier是一个接口，有一个get方法
 *
 */
public class SupplierExample {

    public static void main(String[] args) {
        Supplier<String> supplier = String::new;


        System.out.println(supplier.get());
    }
}
