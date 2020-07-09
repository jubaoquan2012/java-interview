package com.interview.javabinterview;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/2
 */
public class Student implements java.io.Serializable {

    public static void main(String[] args) {

    }

    private static final long serialVersionUID = -6552200931441603436L;

    private String name;

    private int age;

    private int sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
