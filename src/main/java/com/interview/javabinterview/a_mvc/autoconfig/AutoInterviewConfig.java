package com.interview.javabinterview.a_mvc.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/4
 */
@Component
@ConfigurationProperties(prefix = "interview.author")
public class AutoInterviewConfig {

    private String name;

    private String book;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
