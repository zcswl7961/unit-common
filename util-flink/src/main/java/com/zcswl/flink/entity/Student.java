package com.zcswl.flink.entity;

/**
 * 单个数据
 * @author zhoucg
 * @date 2021-02-28 12:09
 */
public class Student {
    /**
     * 学校
     */
    private String school;

    /**
     * 名称
     */
    private String name;
    /**
     * 数量
     */
    private Integer count;


    public Student() {}


    public Student(String school, String name, int count) {
        this.school = school;
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "Student{" +
                "school='" + school + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
