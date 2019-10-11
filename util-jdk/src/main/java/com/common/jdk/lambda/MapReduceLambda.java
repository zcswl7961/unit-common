package com.common.jdk.lambda;
import java.util.Arrays;
import java.util.List;

/**
 * jdk8中的lamdba表达式之对应的Map和Reduce的使用
 * @author: zhoucg
 * @date: 2019-10-10
 */
public class MapReduceLambda {

    /**
     * 之前的价格
     */
    private static List<Integer> costBeforeList = Arrays.asList(100,200,300,400,500);

    public static void main(String[] args) {

        mapMethod(costBeforeList);

        reduceMethid(costBeforeList);
    }

    /**
     * 使用list对应的map()方法
     * map(Function function):将集合元素进行转换
     * @param costBeforeList
     */
    private static void mapMethod(List<Integer> costBeforeList) {
        costBeforeList.stream().map(costBefore -> costBefore + costBefore * 0.12).forEach(System.out::println);
    }

    /**
     * 使用reduce（）方法可以将所有的值进行合并
     * @param costBeforeList
     */
    private static void reduceMethid(List<Integer> costBeforeList) {
        /**
         * 示例，对每一个返回的值进行加上百分值十二的数据之后，然后进行汇总相加的方法
         */
        double bill = costBeforeList.stream().map(cost -> cost + cost *0.12).reduce((sum,cost) -> sum+cost).get();
        System.out.println("数据的总和:"+bill);
    }
    /**
     * reduce方法有三种override的方法
     * Optional<T> reduce(BinaryOperator<T> accumulator);
     * T reduce(T identity, BinaryOperator<T> accumulator);
     * <U> U reduce(U identity,BiFunction<U, ? super T, U> accumulator,BinaryOperator<U> combiner);
     */
}
