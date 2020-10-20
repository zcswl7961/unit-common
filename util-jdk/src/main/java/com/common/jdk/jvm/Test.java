package com.common.jdk.jvm;

/**
 *
 * 关于Class的结构解析
 * @author zhoucg
 * @date 2020-10-15 13:25
 */
public class Test {

    /**
     * ·<clinit>()方法是由编译器自动收集类中的所有类变量的赋值动作和静态语句块（static{}块）中的
     * 语句合并产生的
     * 编译器收集的顺序是由语句在源文件中出现的顺序决定的，静态语句块中只能访问
     * 到定义在静态语句块之前的变量
     * 定义在它之后的变量，在前面的静态语句块可以赋值，但是不能访
     * 问
     */
    static int i = 1; // 如果这个放在static 静态代码块的后面，会出现非法向前引用错误
    static {
        i = 2;
        System.out.println(i);
    }

    private int m;

    public int inc () {
        return m + 1;
    }


    public static void main(String[] args) {
        Child child  = new Child();
    }
}
