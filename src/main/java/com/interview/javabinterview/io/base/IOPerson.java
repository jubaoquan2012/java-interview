package com.interview.javabinterview.io.base;

import java.io.Serializable;

/**
 * Description:
 *
 * @author baoquan.Ju
 * Created at  2020/11/27
 */
public class IOPerson implements Serializable {
    //给类显示声明一个序列版本号。
    private static final long serialVersionUID = 1L;

    private String name;

    private int age;

    public IOPerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

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
}
