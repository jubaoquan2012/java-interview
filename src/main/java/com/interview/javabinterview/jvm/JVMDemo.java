package com.interview.javabinterview.jvm;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/6/17
 */
public class JVMDemo {

    public static void main(String[] args) {
        /**
         * 1.第一行:首先在Eden区分配1个1m的数组,同时会在main线程的虚拟机栈中压入一个main()方法
         *   的帧栈,在main()方法的帧栈内部,会有一个"array1" 变量,这个变量指向堆内存Eden区的一个1M数组
         * 2.第二行第三行,会依次创建三个数组,并且,让局部变量依次指向第二第三个数组
         * 3.第四行:array1 = null ,此时前三个1m的数组无变量引用,成为"垃圾对象"
         *    此时Eden去已经占用了3m内存,还有1m 的内存
         * 4.第五行创建一个 2m的数组,此时Eden区已经放不下了.这时会触发Young GC
         */
        byte[] array1 = new byte[1024 * 1024];
        array1 = new byte[1024 * 1024];
        array1 = new byte[1024 * 1024];
        array1 = null;

        byte[] array2 = new byte[2 * 1024 * 1024];
    }
    /**
     * -XX:NewSize=5m                   新生代5m
     * -XX:MaxNewSize=5m                新生代最大5m
     * -XX:InitialHeapSize=10m          初始堆大小 10m
     * -XX:MaxHeapSize=10m              最大堆大小10m
     * -XX:SurvivorRatio=8              8:1:1  4M : 0.5M : 0.5M
     * -XX:PretenureSizeThreshold=10m   大对象阈值10m
     * -XX:+UseParNewGC
     * -XX:+UseConcMarkSweepGC
     * -XX:+PrintGCDetails
     * -XX:+PrintGCTimeStamps
     * -Xloggc:gc.log                   这个参数可以设置将gc日志写入一个磁盘文件
     * -XX:HandlerPromotionFailure 这个参数打开后会检查: 老年代可用空间是否大于历次Young GC过后进入老年代的对象的平均大小
     */
}
