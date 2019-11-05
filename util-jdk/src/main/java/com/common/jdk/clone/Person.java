package com.common.jdk.clone;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author zhoucg
 * @date 2019-11-05 8:43
 */
public class Person implements Cloneable{


    public static void main(String[] args) throws CloneNotSupportedException {
        Person person = new Person();
        Person person1 = (Person)person.clone();
        System.out.println(person == person1);
    }
}
