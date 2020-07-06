package com.interview.javabinterview.design_pattern.pattern_2;

/**
 * 懒汉式加载
 * 懒汉式加载：最简单的单例模式：2步，
 * 1.把自己的构造方法设置为私有的，不让别人访问你的实例
 * 2.提供一个static方法给别人获取你的实例.
 * @auther: chaErGe
 */
public class Single1 {

    //一个静态实例
    private static Single1 single;

    //私有构造方法
    private Single1() {
    }

    public static Single1 getInstance() {
        //如果null,说明没有初始化
        if (null == single) {
            single = new Single1();
        }
        //返回实例
        return single;
    }
}
