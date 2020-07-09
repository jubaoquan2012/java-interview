package com.interview.javabinterview.jvm.oom;

/**
 * 栈内存溢出演示
 * <p>
 * -XX:+UseParNewGC
 * -XX:+UseConcMarkSweepGC
 * -XX:ThreadStackSize=1m
 * -XX:+PrintGCDetails
 * -XX:+HeapDumpOnOutOfMemoryError
 * -XX:HeapDumpPath=/data
 * -Xloggc:gc.log
 *
 * @author Ju Baoquan
 * Created at  2020/7/8
 */
public class StackOutOfMemory {

    private static long counter = 0;

    public static void main(String[] args) {
        work();
    }

    private static void work() {
        System.out.println("目前是第" + (++counter) + "次调用");
        work();
    }
}
