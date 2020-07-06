package com.interview.javabinterview;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/2
 */
public class DataSource {

    public static void main(String[] args) {
        long[] orgIds = {33503341};

        for (long orgId : orgIds) {
            System.out.println(orgId + " : " + orgId / 100 % 40);
        }
    }
}
