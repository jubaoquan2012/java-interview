package com.interview.javabinterview.util;

/**
 * 重试工具类
 *
 * @author Xu Genquan
 * Create at 2018/1/9
 */
public class RetryUtils {

    private static final int DEFAULT_RETRIES = 2;

    /**
     * 多次重试
     *
     * @param supplier 执行的方法
     */
    public static void retry(Supplier supplier) {
        retry(DEFAULT_RETRIES, supplier);
    }

    /**
     * 多次重试
     *
     * @param times    重试次数
     * @param supplier 执行的方法
     */
    public static void retry(int times, Supplier supplier) {
        Exception exception = null;
        for (int i = 0; i < times; i++) {
            try {
                supplier.get();
                return;
            } catch (RuntimeException e) {
                exception = e;
            }
        }
        throw new RuntimeException("exception");
    }

    /**
     * 配合{@link #retry(int, Supplier)}使用的方法接口
     */
    @FunctionalInterface
    public interface Supplier {
        void get() throws RuntimeException;
    }

    public static void main(String[] args) {

    }
}
