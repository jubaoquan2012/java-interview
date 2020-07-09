package com.interview.javabinterview.jvm.oom;

import com.interview.javabinterview.java_base.map.ArrayList;

import java.util.List;

/**
 *
 *  堆内存溢出展示
 *
 * -Xms10m
 * -Xmx10m
 * -XX:+PrintGCDetails
 * -Xloggc:gc.log
 * -XX:+HeapDumpOnOutOfMemoryError
 * -XX:HeapDumPath=/data
 * -XX:+UseParNewGC
 * -XX:+UseConcMarkSweepGC
 *
 * @author Ju Baoquan
 * Created at  2020/3/25
 */
public class HeapOutOfMemory {

    public static void main(String[] a) {
       long counter = 0;
       List<Object> list = new ArrayList<>();
       while (true){
           list.add(new byte[1024]);
           System.out.println("目前是第" + (++counter) + "次调用");
       }
    }
}
