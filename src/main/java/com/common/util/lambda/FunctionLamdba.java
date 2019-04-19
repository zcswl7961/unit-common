package com.common.util.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by zhoucg on 2019-01-28.
 *
 * @see java.util.function.Function 接收T对象，返回R对象
 * 作用：实现一个一元函数，即传入一个值经过函数计算返回另外一个值
 * 使用场景：
 *      V HashMap.computeIfAbsent(K,Function<K,V> ) //简化函数，如果指定的键尚未与值关联或与null关联，使用函数返回值替换。
 *      R Stream<R> map(Function<? super T,? extends R> mapper) //转换流
 *
 * https://www.cnblogs.com/linzhanfly/p/9686941.html
 *
 * 在java.util.function 包中，含有大量的函数式接口数据，主要分为一下集中基本类型
 * Function 输入参数为类型T，输出为类型R 记作  T --> R
 * Consumer 输入参数为类型T ，输出为void 记作 T--> void
 * Supplier 没有输入参数，输出参数为类型T， 记作 void --> T
 * Predicate 输入参数为类型T，输出类型为boolean 记作  T --> boolean
 *
 * compose 和 andThen 的不同之处是函数执行的顺序不同。
 * andThen就是按照正常思维：先执行调用者，再执行入参的。
 * 然后compose 是反着来的，这点需要注意。
 *
 */
public class FunctionLamdba {

    public static void main(String[] args) {
        ArrayList<ConsumerLambda.Employee> employees = new ArrayList<>();
        String[] prefix = {"B", "A"};
        for (int i = 1; i <= 10; i++)
            employees.add(new ConsumerLambda.Employee(prefix[i % 2] + i, i * 1000));

        //支出
        int[] expenses = ListToArray(employees,employee -> Double.valueOf(employee.getSalary() * (1 + 0.2)).intValue());

        //工资到手
        int[] incomes = ListToArray(employees,employee -> Double.valueOf(employee.getSalary() * 0.8).intValue());


        /**
         * Function函数
         */
        Function<Integer,Integer> times2 = i -> i*2;
        Function<Integer,Integer> suqared = i -> i*i;


        System.out.println(times2.apply(2));
        System.out.println(suqared.apply(4));

        System.out.println(times2.compose(suqared).apply(4));  //32   先4×4然后16×2, 先执行参数，再执行调用者
        System.out.println(times2.andThen(suqared).apply(4));  //64   先4×2,然后8×8, 先执行调用者，再执行参数










    }

    private static int[] ListToArray(List<ConsumerLambda.Employee> list, Function<ConsumerLambda.Employee,Integer> function) {

        int[] ints = new int[list.size()];
        for(int i = 0;i< ints.length ; i++) {
            ints[i] = function.apply(list.get(i));
        }
        return ints;
    }






}
