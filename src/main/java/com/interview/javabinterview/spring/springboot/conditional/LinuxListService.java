package com.interview.javabinterview.spring.springboot.conditional;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/4
 */
public class LinuxListService implements ListService {

    @Override
    public String showListCmd() {
        return "ls";
    }
}
