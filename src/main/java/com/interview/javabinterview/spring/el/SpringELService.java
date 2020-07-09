package com.interview.javabinterview.spring.el;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/4
 */
@Service
public class SpringELService {

    @Value("其他类的属性")
    private String another;

    public String getAnother() {
        return another;
    }

    public void setAnother(String another) {
        this.another = another;
    }
}
