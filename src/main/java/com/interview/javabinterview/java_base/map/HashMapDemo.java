package com.interview.javabinterview.java_base.map;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2019/12/19
 */
public class HashMapDemo {

    /**
     * NUMBER = 10，表示十个线程执行put方法执行了十次
     * 也就是map中总共有10 * 10 = 100 条数据
     */
    public static final int NUMBER = 100;

    public static void main(String[] args) throws InterruptedException {
        Map<String, String> map = new HashMap<>(2);
        for (int i = 0; i < NUMBER; i++) {
            new Thread(new A(map)).start();
        }
    }
}

class A implements Runnable {

    Map<String, String> map;

    public A(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < 10000; i++) {
                map.put(Thread.currentThread().getName() + i, "test_" +Thread.currentThread().getName()+"_"+ System.currentTimeMillis());
            }
            for (int i = 0; i < 10000; i++) {
                String s = map.get(Thread.currentThread().getName() + i);
                System.out.println(s);
            }
        }
    }
}

