package com.interview.javabinterview.design_pattern.single;

/**
 * 使用枚举
 * @auther: chaErGe
 */
public enum Single7 {

    SINGLE_7;
    public Single7 getInstance(){
        return SINGLE_7;
    }

    /**
     * 为什么使用枚举可以呢？枚举类型反编译之后可以看到实际上是一个继承自Enum的类。
     * 所以本质还是一个类。 因为枚举的特点，你只会有一个实例
     *
     * Single7枚举继承了java.lang.Enum<> 类。事实上就是一个类，但是我们这样就能防止反射破坏我们辛苦写的单例模式了。
     * 因为枚举的特点，而他也能保证单例。堪称完美
     */


    /**
     * 总结:
     *      对于频繁使用的对象，可以省略创建对象所花费的时间，这对于那些重量级对象而言，是非常可观的一笔系统开销；
     *      由于 new 操作的次数减少，因而对系统内存的使用频率也会降低，这将减轻 GC压力，缩短 GC 停顿时间。
     *
     */

}
