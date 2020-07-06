package com.interview.javabinterview.java_base.jdk8;

/**
 * 函数式接口定义
 *
 * @author Ju Baoquan
 * Created at  2020/4/16
 */
public class FunctionInterface {

    private static void test1() {
        Converter<String, Integer> converter = Integer::valueOf;
        Integer converted = converter.convert("123");
        System.out.println(converted);    // 123
    }

    private static void test2() {
        Converter<String, Integer> converter = (from) -> Integer.valueOf("1" + from);
        Integer converted = converter.convert("123");
        System.out.println(converted);    // 123
    }

    @FunctionalInterface
    interface Converter<F, T> {

        T convert(F from);
    }
}
