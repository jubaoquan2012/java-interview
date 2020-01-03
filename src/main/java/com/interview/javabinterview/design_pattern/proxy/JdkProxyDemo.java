package com.interview.javabinterview.design_pattern.proxy;

/**
 * 说明
 *
 * @author Ju Baoquan
 * Created at  2019/9/16
 */
public class JdkProxyDemo {

    public static void main(String[] args) {
        Student student = new Student();
        JDKProxy jdkProxy = new JDKProxy(student);
        Person person = (Person) jdkProxy.getInstance();
        person.getName();
        person.getAge();
        person.toString();
    }
}
