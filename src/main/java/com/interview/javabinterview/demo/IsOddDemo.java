package com.interview.javabinterview.demo;

/**
 * 判断一个整数是否是奇数
 *
 * @author Ju Baoquan
 * Created at  2020/3/4
 */
public class IsOddDemo {

    /**
     * 最初方法
     * <p>
     * 优化
     */
    public boolean isOdd_1(int i) {
        return i % 2 == 1;
    }

    /**
     * 如果传入 -1
     * <p>
     * 优化
     */
    public boolean isOdd_2(int i) {
        return i % 2 == 1 || i % 2 == -1;
    }

    /**
     * 如果传入 -1
     * <p>
     * 继续优化
     */
    public boolean isOdd_3(int i) {
        return i % 2 != 0;
    }

    /**
     * 奇数和偶数转换成二进制有什么区别？
     * “奇数最后一位是1，偶数最后一位是0”
     * <p>
     * 继续优化
     */
    public boolean isOdd_4(int i) {
        return i >> 1 << 1 != i;
    }

    /**
     * 奇数和偶数转换成二进制有什么区别？
     * “奇数最后一位是1，偶数最后一位是0”
     * <p>
     * 继续优化
     */
    public boolean isOdd_5(int i) {
        return (i & 1) == 1;
    }
}
