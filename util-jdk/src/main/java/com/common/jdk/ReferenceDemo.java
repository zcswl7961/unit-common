package com.common.jdk;

/**
 * 值传递分析
 * @author zhoucg
 * @date 2021-04-07 13:23
 */
public class ReferenceDemo {

    public static void main(String[] args) {

        Obj obj = new Obj();
        obj.a = "测试";

        System.out.println(obj);
        Obj objTmp = obj;
        test(objTmp);


        System.out.println(obj.a);
    }
    private static Obj test(Obj obj) {
        System.out.println(obj.a);
        Obj inner = new Obj();
        inner.a = "bbb";
        obj = inner;
        return inner;
    }
}
