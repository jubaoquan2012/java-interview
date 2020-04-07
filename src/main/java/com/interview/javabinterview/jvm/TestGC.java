package com.interview.javabinterview.jvm;

import java.util.concurrent.TimeUnit;

/**
 * VM Args:-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:+PrintGCTimeStamps  -XX:+UseSerialGC -XX:SurvivorRatio=8
 *
 * @author Ju Baoquan
 * Created at  2020/3/25
 */
public class TestGC {
    private static final int _1MB = 1024 * 1024;
    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];    // 这里会出现一次 Minor GC
    }
}
