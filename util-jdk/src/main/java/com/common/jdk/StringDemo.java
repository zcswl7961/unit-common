package com.common.jdk;

/**
 * @author zhoucg
 * @date 2021-02-02 15:14
 */
public class StringDemo {

    /**
     * 成员变量
     * 静态基本数据类型的成员变量的内存分布情况
     * 1，首先，静态（非final）类型的基础数据类型的成员变量在准备阶段会进行内存分配，这里的内存值得是静态区（方法区中）
     * 2，静态（非final）类型的基础数据类型的数据初始化（赋值）阶段是在类加载的初始化过程：
     */
    //private static int x = 1;

    public static void main(String[] args) {
        String s0 = "zhou";
        String s1 = "zhoucg";
        String s2 = "zhoucg";
        String s3 = new String(s1);
        String s4 = new String("zhoucg");
        String s5 = "zhou"+"cg";
        String s6 = s0 + "cg";
        System.out.println(s1 == s2); // true
        System.out.println(s1 == s3); // false
        System.out.println(s3 == s4); // false
        // 在"+"两边都是常量字符串，则将两个字符串合并并且在String Pool中查找"zhoucg"
        // 并返回在String Pool中的内存地址正好也是zhoucg变量的内存地址，所以第一句代码会输出true。
        System.out.println(s1 == s5); // true
        // 如果两边有一边是引用类型变量，Java会将合并成一个字符串并且在堆栈中创建一个 新的对象并且返回内存地址，所以这句代码是输出false。
        System.out.println(s1 == s6); // false


        // 分析对应java中的传递是指传递还是引用传递
        String s7 = "zhoucg";
        test(s7);
        System.out.println(s7);

        // 分析对应的基本类型的传值操作
        int x = 6;
        testX(x);
        System.out.println(x);
        
        // 基本数据类型的内存分配情况
        int x1 = 3;
        int x2 = x1;
        x1 = 4;
        System.out.println(x1);
        System.out.println(x2);

        // 基本数据类型是存在于栈内存区域中
        int x22 = 3;
        int x21 = 3;
        x22 = 4;
        System.out.println(x21);
        System.out.println(x22);


        // 引用类型传递
        User user = new User();
        user.setName("zhoucg");
        testU(user);
        System.out.println(user.getName());

        String xxx = "zhoucgwl";
        String s11 = new String("zhoucg");
        testS(s11);
        System.out.println(s11 == xxx);
        System.out.println(s11);
    }


    private static void test(String s) {
        s += "wl";
    }

    private static void testX(int x) {
        x = 2;
    }

    private static void testU(User user) {
        user.setName("wl");
    }

    private static void testS(String s11){
        s11 = "zhoucgwl";
    }


    private static class User {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
