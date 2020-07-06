package com.interview.javabinterview.design_pattern.pattern_2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 饿汉式加载单例
 * @auther: chaErGe
 */
public class Single2 {
    /**
     *  类加载的时候就加载实例化对象
     */
    private static final Single2 single = new Single2();

    public static Single2 getInstance(){
        return single;
    }

    private Single2() {
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public  String formatDate(LocalDateTime date) {
        return formatter.format(date);
    }

    public  LocalDateTime parse(String dateNow) {
        return LocalDateTime.parse(dateNow, formatter);
    }
}
