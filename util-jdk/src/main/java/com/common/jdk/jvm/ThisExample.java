package com.common.jdk.jvm;

/**
 * this 指针
 * @author zhoucg
 * @date 2021-02-18 10:08
 */
public class ThisExample {


    public static void main(String[] args) {
        InnerChild innerChild = new InnerChild();
        String name = innerChild.getName();
        System.out.println(name);
    }
}

class InnerChild extends InnerParent {
}


class InnerParent {

    /**
     * current name
     */
    private String name;

    protected InnerParent() {
        Class aClass = this.getClass();
        String name1 = aClass.getName();
        setName(name1);
    }


    private void setName(String name) {
        this.name = "ZHOUCG:" + name + ":WL";
    }

    protected String getName() {
        return this.name;
    }

}
