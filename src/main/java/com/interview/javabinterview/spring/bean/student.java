package com.interview.javabinterview.spring.bean;

import org.springframework.beans.factory.BeanNameAware;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/21
 */
public class BeanLifeCycleDemo implements BeanNameAware {

    private String name;

    public BeanLifeCycleDemo(){
        super();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("设置对象属性 setName");
        this.name = name;
    }

    public void initBeanLifeCycleDemo(){
        System.out.println("BeanLifeCycleDemo 这个bean:初始化");
    }

    public void destroyBeanLifeCycleDemo(){
        System.out.println("BeanLifeCycleDemo 这个bean:销毁");
    }

    public void play(){
        System.out.println("BeanLifeCycleDemo 这个Bean:使用");
    }

    @Override
    public String toString() {
        return "BeanLifeCycleDemo{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("调用BeanNameAware的setBeanName");
    }
}
