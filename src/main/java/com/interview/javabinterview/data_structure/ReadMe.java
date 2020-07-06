package com.interview.javabinterview.data_structure;

import java.util.*;

/**
 * 集合:     {@link List} 、{@link Set }、{@link Map} 都是接口,前两个继承Collection接口,Map 为独立接口
 * Set:     {@link HashSet }、{@link LinkedHashSet}、{@link TreeSet}
 * List:    {@link ArrayList}、{@link LinkedList}、{@link Vector}
 * Map:     {@link HashMap}、{@link LinkedHashMap}、{@link Hashtable}、{@link TreeMap}
 *
 * @author Ju Baoquan
 * Created at  2020/5/15
 */
public class ReadMe {

    /**
     * 一: List 有序、可重复
     *     --ArrayList
     *      1. 数据结构: 数组
     *      2. 扩容: 1.5倍
     *      3. 插入效率低: 原因
     *          1).当数组空间不足需要扩容的时候,扩容后需要数组拷贝
     *          2).当不在数组末尾插入数据,需要移动数组元素
     *      4. 删除效率为何说正常情况下ArrayList低，LinkedList高呢？
     *         答：因为删除效率高、低不是绝对的。其实删除操作可以分为两部分。
     * ​         1).找到要删除的元素，这个通过索引找，ArrayList的执行效率要远高于LinkedList的执行效率；
     *              通过equals找则需要遍历整个集合，ArrayList和LinkedList执行效率基本一致。
     * ​         2).删除元素及后续操作，这个如果删除是最后一个元素，执行效率基本一致；如果是删除的中间元素，
     *              那么ArrayList需进行数组元素移动，而LinkedList只需搭建起该元素的上一个节点和下一个节点的关系即可，
     *              LinkedList执行效率高于ArrayList。
     *
     *
     *     --LinkedList
     *      1.数据结构:  双向链表
     *      2. 尾部插入
     *      3. get(index):index 与 size/2 比较,
     *              如果index小,那么从头开始从前往后遍历
     *              如果index大,那么从尾开始从前往后遍历
     *
     *      4. 插入效率高: 省去了ArrayList插入数据可能的数组扩容和数据元素移动时所造成的开销
     *
     *
     *     --Vector
     *      1. 数据结构:: 数组
     *      2. 扩容: 2倍
     *
     *
     *
     * 二: Set 无序、唯一
     *     --HashSet
     *      数据结构: 底层数据结构是哈希表(无序、唯一) 底层其实就是包装了HashMap,
     *              元素放在HashMap的key值上
     *      如何来保证元素唯一性?
     *      1. 底层封装了HashMap 就是一个皮包公司.
     *      2. add(e)-->hashMap.put(e,PRESENT) PRESENT: 就是一个空对象: private static final Object PRESENT = new Object();
     *
     *     --LinkedHashSet: 继承了Hash
     *      1. 数据结构:
     *          1) 继承了 HashSet :
     *          2).构造函数: new LinkedHashSet 的时候走的: Map map = new LinkedHashMap<>(initialCapacity, loadFactor);
     *          所以拥有: LinkedHashMap的特性
     *      2. 增删查利用的HashSet, 存储用的 LinkedHashMap
     *      3. FIFO 有序
     *
     *     --TreeSet
     *       https://www.jianshu.com/p/12f4dbdbc652
     *      （1）TreeSet继承于AbstractSet，并且实现了NavigableSet接口。
     *      （2）TreeSet是一个包含有序的且没有重复元素的集合，通过TreeMap实现。TreeSet中含有一个"NavigableMap类型的成员变量"m，
     *          而m实际上是"TreeMap的实例"。
     *
     *
     * 三: Map
     *     --HashMap (无序)
     *      1. 数组 + 单向链表 + 红黑树
     *      2. 线程不安全
     *      3. key和value均  可以   为null
     *      4. 元素不允许重复
     *
     *     --Hashtable (无序)
     *      1. 数组 + 单向链表
     *      2. 线程安全: synchronized
     *      3. key和value均  不可以 为null
     *      4. 元素不允许重复
     *
     *     --TreeMap (有序)
     *          https://www.cnblogs.com/LiaHon/p/11221634.html
     *      1. 存储 K-V键值对, 通过红黑树(R-B tree)实现,红黑树结构天然支持有序.
     *
     * 四: 扩展
     *     -- LinkedHashMap: 包装了 hashMap;
     *       https://mp.weixin.qq.com/s/2MAZldmPL_BORxIKoPh09w
     *      （1）LinkedHashMap继承自HashMap，具有HashMap的所有特性；
     *      （2）LinkedHashMap内部维护了一个双向链表存储所有的元素；
     *      （3）如果accessOrder为false，则可以按插入元素的顺序遍历元素；
     *      （4）如果accessOrder为true，则可以按访问元素的顺序遍历元素；
     *      （5）LinkedHashMap的实现非常精妙，很多方法都是在HashMap中留的钩子（Hook），直接实现这些Hook就可以实现对应的功能了，并不需要再重写put()等方法；
     *      （6）默认的LinkedHashMap并不会移除旧元素，如果需要移除旧元素，则需要重写removeEldestEntry()方法设定移除策略；
     *      （7）LinkedHashMap可以用来实现LRU缓存淘汰策略；
     *
     *       (8) 遍历 keySet走的是LinkedHashMap
     *
     *
     * 五: 红黑树的特点
     *    1.节点分为红色或者黑色；
     *    2.根节点必为黑色；
     *    3.叶子节点都为黑色，且为null；
     *    4.连接红色节点的两个子节点都为黑色（红黑树不会出现相邻的红色节点）；
     *    5.从任意节点出发，到其每个叶子节点的路径中包含相同数量的黑色节点；
     *    6.新加入到红黑树的节点为红色节点；
     *
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */
}
