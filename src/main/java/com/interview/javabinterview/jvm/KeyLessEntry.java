package com.interview.javabinterview.jvm;

import java.util.HashMap;
import java.util.Map;

/**
 * 内存泄漏 : java.lang.OutOfMemoryError: GC overhead limit exceeded
 * VM Args:-Xmx4m -XX:+PrintGCDetails
 * <p>
 * Sun 官方对此的定义：超过98%的时间用来做 GC 并且回收了不到 2% 的堆内存时会抛出此异常
 * <p>
 * 执行以下代码就会发生内存泄漏，第一次循环，map 里存有 10000 个 key value,
 * 但之后的每次循环都会新增 10000 个元素，因为 Key 这个 class 漏写了 equals 方法，
 * 导致对于每一个新创建的 new Key(i) 对象，即使 i 相同也会被认定为属于两个不同的对象，
 * 这样 m.containsKey(new Key(i)) 结果均为 false,结果就是 HashMap 中的元素将一直增加，
 * 解决方式也很简单，为 Key 添加 equals 方法即可，如下
 *
 * @author Ju Baoquan
 * Created at  2020/3/25
 */
public class KeyLessEntry {

    static class Key {

        Integer id;

        Key(Integer id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        // 解决就需要重写 equals 方法
        //        @Override
        //        public boolean equals(Object obj) {
        //            if (obj instanceof Key) {
        //                return ((Key) obj).id.equals(this.id);
        //            }
        //            return false;
        //        }
    }

    public static void main(String[] args) {
        Map<Key, String> m = new HashMap<Key, String>();
        while (true) {
            for (int i = 0; i < 10000; i++) {
                if (!m.containsKey(new Key(i))) {
                    m.put(new Key(i), "Number:" + i);
                }
            }
        }
    }
}
