package com.interview.javabinterview.spring.reflect.target;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/22
 */
@CarAnnotation
public class BMWCarImpl extends CarAbstract {

    @Override
    public String brand() {
        return "此车是宝马";
    }
}
