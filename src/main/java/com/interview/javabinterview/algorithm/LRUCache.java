package com.interview.javabinterview.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * lru算法, 最近最少使用.  内置缓存队列
 *
 * @author Ju Baoquan
 * Created at  2020/3/16
 */
public class LRUCache<k, v> {

    // 容量
    private int capacity;

    // 记录缓冲中的数量
    private int count;

    //缓存
    private Map<k, Node<k, v>> cacheMap;

    private Node head;

    private Node tail;

    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(String.valueOf(capacity));
        }
        this.capacity = capacity;
        this.cacheMap = new HashMap<>();
        //设置哨兵节点, 减少代码中 头尾节点的空判断
        Node headNode = new Node(null, null);
        Node tailNode = new Node(null, null);
        headNode.next = tailNode;
        tailNode.pre = headNode;

        this.head = headNode;
        this.tail = tailNode;
    }

    public void put(k key, v value) {
        Node<k, v> node = cacheMap.get(key);
        if (node == null) {
            //判断count是否已满
            if (count >= capacity) {
                //移除最后一个节点
                removeTailNode();
            }
            node = new Node(key, value);
            //头部插入,作为第一个节点
            addNode(node);
        } else {
            //如果存在,并且不是在头部,移动节点到头部
            if (node.pre != head) {
                moveNodeToHead(node);
            }
        }
    }

    public Node<k, v> get(k key) {
        Node<k, v> node = cacheMap.get(key);
        if (node != null) {
            // 移动节点到头部
            moveNodeToHead(node);
        }
        return node;
    }

    private void moveNodeToHead(Node<k, v> node) {
        removeNode(node);
        addToHead(node);
    }

    /**
     * 添加到头结点的第一个节点
     *
     * @param node node
     */
    private void addNode(Node<k, v> node) {
        // 从头部添加一个节点
        addToHead(node);
        cacheMap.put(node.key, node);
        count++;
    }

    private void addToHead(Node<k, v> node) {
        Node next = head.next;
        next.pre = node;
        node.next = next;

        head.next = node;
        node.pre = head;
    }

    /**
     * 移除最后一个节点
     */
    private void removeTailNode() {
        Node node = tail.pre;
        // 移除一个节点
        removeNode(node);
        // 从缓存中移除
        cacheMap.remove(node.key);
        count--;
    }

    private void removeNode(Node node) {
        Node pre = node.pre;
        Node next = node.next;
        pre.next = next;
        next.pre = pre;

        node.pre = null;// help GC
        node.next = null;
    }

    class Node<k, v> {

        k key;

        v value;

        Node pre;

        Node next;

        public Node(k key, v value) {
            this.key = key;
            this.value = value;
        }
    }
}
