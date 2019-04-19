package com.common.util.proxy.staticProxy;

/**
 * Created by zhoucg on 2019-04-18.
 * 代理类，代理Student
 */
public class StudentsProxy implements Person{

    Student student;

    public StudentsProxy(Person student) {
        if(student.getClass() == Student.class) {
            this.student = (Student)student;
        }
    }

    @Override
    public void giveMoney() {
        student.giveMoney();
    }
}
