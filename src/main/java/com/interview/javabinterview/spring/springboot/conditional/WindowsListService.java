package com.interview.javabinterview.spring.springboot.conditional;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/4
 */
public class WindowsListService  implements ListService {

    @Override
    public String showListCmd() {
        return "dir";
    }
}
