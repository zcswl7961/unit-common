package com.common.jdk.lambda;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Created by zhoucg on 2019-01-28.
 *
 * @see java.util.function.Consumer  接收T对象，不返回值
 * 作用：消费某个对象
 * 使用场景：Iterator 接口的forEach 方法需要 传入Consumer ，大部分集合类都实现了该方法，用于返回Iterator
 * 对象进行迭代
 *
 * https://www.cnblogs.com/linzhanfly/p/9686941.html
 */
public class ConsumerLambda {


    static class Employee {
        private String name;
        private int salary;

        public Employee() {
            this.salary = 4000;
        }

        public Employee(String name, int salary) {
            this.name = name;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSalary() {
            return salary;
        }

        public void setSalary(int salary) {
            this.salary = salary;
        }

        @Override
        public String toString() {
            return new StringBuilder()
                    .append("name:").append(name)
                    .append(",salary:").append(salary)
                    .toString();
        }
    }

    //输出要交税的员工
    static class SalaryConsumer implements Consumer<Employee> {

        @Override
        public void accept(Employee employee) {

            if(employee.getSalary() > 200) {
                System.out.println(employee.getName() + "要开始缴税啦");
            }

        }
    }

    //输出需要名字前缀是A的显示出来
    static class NameConsumer implements Consumer<Employee> {

        @Override
        public void accept(Employee employee) {
            if(employee.getName().startsWith("A")) {
                System.out.println(employee.getName()+" salary is " + employee.getSalary());
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        String[] prefix = {"A", "B"};
        for (int i = 1; i <= 10; i++)
            employees.add(new Employee(prefix[i % 2] + i, i * 1000));


        /**
         * 实现接口
         */
        employees.forEach(new SalaryConsumer());
        employees.forEach(new NameConsumer());

        /**
         * 使用lamdba表达式进行转换
         */
        employees.forEach(employee -> {
            if(employee.getSalary() > 100) {
                System.out.println("你需要交税啦：" + employee.getName());
            }
        });
    }
}
