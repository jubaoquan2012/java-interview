package com.interview.javabinterview.java_base.final_static;


/**
 * static final和final的区别
 *
 * @author: Ju Baoquan
 * Created at:  2019/4/9
 */

public class StaticFinalTest {

    private int a;

    public void test1() {
        System.out.println("a=" + a);
    }

    public void test2() {
        int size = 5;
        for (int i = 0; i < size; i++) {
            WithFinalFields withFinalFields1 = new WithFinalFields();
            System.out.println(withFinalFields1);
        }

        /**
         * 打印结果
         * static final:wff= SelfCounter: 1,
         * final:wff= SelfCounter: 0
         *
         * static final:wff= SelfCounter: 2,
         * final:wff= SelfCounter: 0
         *
         * static
         *  (1).static标记 保证此属性只有一个
         *  (2).static标记static在类加载时初始化
         *
         * final :
         *  修饰类、方法以及成员变量。
         * (1).final标记的类不能被继承;
         * (2).final标记的方法不能被子类复写;
         * (3).final标记的变量即成为常量,只能被赋值一次,
         *      对于基本类型，不可变指的是值;
         *      对于引用类型，不可变指的是引用地址
         *      final 修饰的时候必须初始化:在定义中/构造器中 ***
         *      static final 修饰的时候必须初始化:只能在在定义中 ****
         * (4).final标记的形参 public void say(final int i){传进去之后只读,不可修改}
         */
    }
}
