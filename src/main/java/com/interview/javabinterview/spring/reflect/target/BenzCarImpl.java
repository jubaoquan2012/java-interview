package com.interview.javabinterview.spring.reflect.target;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/5/22
 */
@CarAnnotation(version = "2008款", price = 28000, color = {"黑", "白"})
public class BenzCarImpl extends CarAbstract {

    @Override
    public String brand() {
        return "此车是奔驰";
    }

    @Override
    public void run(int speed) {
        System.out.println("此车最高时速是:" + speed);
    }
}
