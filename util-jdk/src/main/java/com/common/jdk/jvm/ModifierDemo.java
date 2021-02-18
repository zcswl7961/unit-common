package com.common.jdk.jvm;

/**
 * 测试类喝结构的Modifier类型修饰
 * @author zhoucg
 * @date 2021-02-07 13:49
 */
public class ModifierDemo {

    public static void main(String[] args) {

        /**
         *  public static final int PUBLIC           = 0x00000001;
         *  public static final int PRIVATE          = 0x00000002;
         *  public static final int PROTECTED        = 0x00000004;
         */
        Class clazz = ModifierDemo.InnerPrivateClass.class;

        int modifiers = clazz.getModifiers();
        System.out.println(modifiers);
        String name = clazz.getName();
        System.out.println(name);

        int modifiers1 = ModifierDemo.class.getModifiers();
        System.out.println(modifiers1);



    }

    public static class InnerPrivateClass {

        private String name;
    }
}
