package com.interview.javabinterview.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/21
 */
public class student implements BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean {

    private String name;

    private int age;

    private BeanFactory beanFactory;

    private String beanName;

    public student() {
        System.out.println("【构造器】 调用Student的构造器实例化");
        //super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("【注入属性】name");
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        System.out.println("【注入属性】age");
        this.age = age;
    }

    // BeanFactoryAware接口方法
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("【BeanFactoryAware接口】调用setBeanFactory方法");
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("【BeanNameAware接口】调用setBeanName方法");
        this.beanName = s;
    }

    public String getBeanName() {
        return beanName;
    }

    public void myInit() {
        System.out.println("【init-method】调用<bean>的init-method属性指定的初始化方法");
    }

    public void myDestroy() {
        System.out.println("【destroy-method】调用<bean>的destroy-method属性指定的初始化方法");
    }

    public void play() {
        System.out.println("student 这个Bean:使用");
    }

    @Override
    public String toString() {
        return "student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", beanFactory=" + beanFactory +
                ", beanName='" + beanName + '\'' +
                '}';
    }

    //这是InitializingBean接口方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("【InitializingBean接口】调用afterPropertiesSet方法");
    }

    // 这是DisposableBean接口方法
    @Override
    public void destroy() throws Exception {
        System.out.println("【DisposableBean接口】调用destroy方法");
    }
}
