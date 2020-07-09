package com.interview.javabinterview.jvm;

import com.interview.javabinterview.java_base.map.ArrayList;

import java.util.List;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/30
 */
public class JVMDemo4 {

    public static void main(String[] args) throws InterruptedException {
        List<Data> datas = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            datas.add(new Data());
        }
        Thread.sleep(1 * 60 * 60 * 1000);
    }

    static class Data {

    }
}
