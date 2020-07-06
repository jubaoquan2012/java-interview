package com.interview.javabinterview.lamdba.exam;

/**
 * 接口
 *
 * @author Ju Baoquan
 * Created at  2020/5/28
 */
public interface NoParameterService {

    void run();

    void oneParameter(String a);

    void twoParameter(String a, String b);

    String noParameterWithResult();

    String oneParameterWithResult(String a);

    String twoParameterWithResult(String a, String b);
}
