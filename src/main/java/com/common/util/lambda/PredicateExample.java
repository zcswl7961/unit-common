package com.common.util.lambda;


import com.google.common.collect.Lists;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.List;

/**
 * Created by zhoucg on 2019-04-16.
 * Predicate:断言，进行判断
 */
public class PredicateExample {

    public List<Integer> conditionFilterAdd(List<Integer> list, Predicate<Integer> predicate, Predicate<Integer> predica) {
        return list.stream().filter(predicate.and(predica)).collect(Collectors.toList());
    }

    public List<Integer> conditionFilterOr(List<Integer> list, Predicate<Integer> predicate,Predicate<Integer> predicate2){
        return list.stream().filter(predicate.or(predicate2)).collect(Collectors.toList());
    }

    public List<Integer> conditionFilterNegate(List<Integer> list, Predicate<Integer> predicate){
        return list.stream().filter(predicate.negate()).collect(Collectors.toList());
    }


    public static void main(String[] args) {

        List<Integer> list = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10);
        PredicateExample predicateTest = new PredicateExample();

        List<Integer> result;


        //大于5并且是偶数
        result = predicateTest.conditionFilterAdd(list, integer -> integer > 5, integer1 -> integer1 % 2 == 0);
        result.forEach(System.out::println);//6 8 10
        System.out.println("-------");

        //大于5或者是偶数
        result = predicateTest.conditionFilterOr(list, integer -> integer > 5, integer1 -> integer1 % 2 == 0);
        result.forEach(System.out::println);//2 4 6 8 9 10
        System.out.println("-------");

        //条件取反
        result = predicateTest.conditionFilterNegate(list,integer2 -> integer2 > 5);
        result.forEach(System.out::println);// 1 2 3 4 5
        System.out.println("-------");
    }
}
