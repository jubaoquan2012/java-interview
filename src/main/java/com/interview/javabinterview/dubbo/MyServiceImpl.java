package com.interview.javabinterview.dubbo;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/2
 */
public class MyServiceImpl implements MyService {

    @Override
    public String doMyTest(String field1, String field2) {
        return field1 + field2;
    }
}
