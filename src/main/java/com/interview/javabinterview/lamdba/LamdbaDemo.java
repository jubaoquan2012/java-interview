package com.interview.javabinterview.lamdba;

import com.interview.javabinterview.lamdba.exam.*;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/28
 */
public class LamdbaDemo {

    public static void main(String[] args) {
        method();
    }

    private static void method() {
        /**
         * 无返回值类型
         */
        NoParameterService noParameterService = () -> PrintImpl.printWithOneParameter("无惨--无返回值");
        noParameterService.run();

        OneParameterService oneParameterService = (String x) -> PrintImpl.printWithOneParameter(x);
        oneParameterService.run("一个参数--无返回值");

        OneParameterService oneParameterService1 = (x) -> PrintImpl.printWithOneParameter(x);
        oneParameterService1.run("一个参数--无返回值,省略参数类型");

        OneParameterService oneParameterService2 = PrintImpl::printWithOneParameter;
        oneParameterService2.run("一个参数--无返回值,省略括号、类型、参数");

        TwoParameterService twoParameterService = (x, y) -> {
            PrintImpl.printWithTwoParameter(x, y);
        };
        twoParameterService.run("两个参数", "无返回值");

        TwoParameterService twoParameterService1 = (x, y) -> PrintImpl.printWithTwoParameter(x, y);
        twoParameterService1.run("两个参数", "无返回值");

        TwoParameterService twoParameterService2 = PrintImpl::printWithTwoParameter;
        twoParameterService1.run("两个参数", "无返回值");

        /**
         * 有返回值类型
         */
        NoParameterWithResult noParameterWithResult = () -> {
            return "无参,有返回值";
        };
        System.out.println(noParameterWithResult.run());

        NoParameterWithResult noParameterWithResult1 = () -> "无参,有返回值";
        System.out.println(noParameterWithResult1.run());

        OneParameterWithResult oneParameterWithResult = (x) -> {
            return "一个参数,有返回值:" + x;
        };
        System.out.println(oneParameterWithResult.run("hello"));

        OneParameterWithResult oneParameterWithResult1 = (x) -> "一个参数,有返回值:" + x;
        System.out.println(oneParameterWithResult.run("hello"));

        OneParameterWithResult oneParameterWithResult2 = (x) -> {
            PrintImpl.printWithOneParameter(x);
            return "";
        };
        System.out.println(oneParameterWithResult2.run("hello"));

        TwoParameterWithResult twoParameterWithResult = (x, y) -> {
            return "两个参数,有返回值" + x + ":" + y;
        };
        System.out.println(twoParameterWithResult.run("hello", "word"));
    }
}
