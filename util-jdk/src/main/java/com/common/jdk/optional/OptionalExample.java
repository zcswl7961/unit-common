package com.common.jdk.optional;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;
import java.util.function.Function;

/**
 * <p>
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-06 21:54
 */
public class OptionalExample {

    public static void main(String[] args) {

        Person person = new Person("zcg","杭州市西湖区");

        /**
         * ofNullable（T t） 获取一个可为null的Optional
         */
        Optional<Object> o = Optional.ofNullable(null);

        Optional<Object> o1 = Optional.of(null);
        boolean present = o.isPresent();

        /**
         * OptionalTest.ofNullable（）
         * OptionalTest.map(Function)
         */
        Optional.ofNullable(person).map(Person::getAddress).ifPresent(System.out::print);

        /**
         * OptionalTest.of(T t) is null return NullPointerException
         */
        Optional.of(null).ifPresent(System.out::print);

        Function mapper = Optional::of;
        Optional.ofNullable(person).flatMap(mapper).ifPresent(System.out::print);

        /**
         * between map() and flagMap()
         * map是把结果自动封装成一个Optional，但是flatmap需要你自己去封装。
         * https://www.cnblogs.com/lijingran/p/8727149.html
         */

        Optional<String> s = Optional.of("input");
        s.flatMap(OptionalExample::flagMap).ifPresent(System.out::print);
        s.map(OptionalExample::map).ifPresent(System.out::print);

        /**
         * get a normal
         *
         * https://www.cnblogs.com/lijingran/p/8727149.html
         */
        Function flagMap1 = p -> Optional.ofNullable(p);
        Function flagMap2 = p -> Optional.of("zhoucg" + p);
        s.flatMap(flagMap1).flatMap(flagMap2).ifPresent(System.out::print);

    }

    static Optional<String> flagMap(String input) {
        return input == null ? Optional.empty() : Optional.of("out put "+input);
    }

    static String map(String input) {
        return input == null ? null : "output for " + input;
    }

    @Data
    @AllArgsConstructor
    private static class Person {

        private String name;

        private String address;
    }
}
