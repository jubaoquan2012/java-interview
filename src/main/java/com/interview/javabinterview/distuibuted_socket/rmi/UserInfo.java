package com.interview.javabinterview.distuibuted_socket.rmi;

import java.io.Serializable;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/1
 */
public class UserInfo implements Serializable {

    private String userName;
    private String userDesc;
    private Integer userAge;
    private Boolean userSex;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public Boolean getUserSex() {
        return userSex;
    }

    public void setUserSex(Boolean userSex) {
        this.userSex = userSex;
    }
}
