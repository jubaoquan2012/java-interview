package com.interview.javabinterview.a_algorithm;

import com.interview.javabinterview.c_data_structure.HashMap;

/**
 * 链表/数组反转
 *
 * @author Ju Baoquan
 * Created at  2020/5/12
 */
public class Algorithm_4 {

    public class DoubleNode {

        int value;

        DoubleNode pre;

        DoubleNode next;
    }

    public class Node {

        int value;

        Node next;
    }

    public static void main(String[] args) {
        int[] array = Algorithm_Base.array;
        int[] arraySort = Algorithm_Base.arraySort;
    }

    /**
     * 单向链表反转
     *
     * @param node
     */
    private static Node reverseNode(Node node) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        if (node == null || node.next == null) {
            return node;
        }
        //保存前一个节点
        Node pre = null;
        Node next = null;
        while (node != null) {
            next = node.next;
            //反转
            node.next = pre;
            //保存当前节点为下次循环的前一个节点
            pre = node;
            //为下次循环准备
            node = next;
        }
        return pre;
    }

    /**
     * 双向链表反转
     *
     * @param node
     */
    private static DoubleNode reverseDoubleNode(DoubleNode node) {
        if (node == null || node.next == null) {
            return node;
        }
        DoubleNode pre = null;
        DoubleNode next = null;
        while (node != null) {
            next = node.next;
            //反转
            node.next = pre;
            node.pre = next;
            //保存当前节点为下次循环的前一个节点
            pre = node;
            //为下次循环准备
            node = next;
        }
        return pre;
    }

    /**
     * 单向链表部分反转
     * 1->2->3->4->5->6->7
     * 1->2->6->5->4->3->7
     * 要求:
     * 1.给定一个单向链表的头结点head,以及两个整数from和to，在单向链表上把第from个节点到第to个节点这一部分进行反转。
     * 2.如果不满足1<=from<=to<=N，则不用调整。
     */
    private static Node reversePartNode(Node node, int from, int to) {
        int len = 0;
        Node nodeTemp = null;
        Node fromNode = null;
        Node toNode = null;
        //例如:先找到 2--5的节点 即 1 和 6
        while (node != null) {
            len++;
            nodeTemp = node.next;
            //找到from节点的前一个节点
            if (len == from - 1) {
                fromNode = nodeTemp;
            }
            //找到to节点的后一个节点
            if (len == to + 1) {
                toNode = nodeTemp;
            }
            node = nodeTemp;
        }
        if (from > to || from < 1 || to > len) {
            return node;
        }
        // 找到口开始反转的位置
        Node newHead = fromNode.next;// newHead = Node(3)
        //将要反转的第一个节点Node(3)和toNode(7)链接起来: 例如: 3->7
        newHead.next = toNode;
        Node pre = null;
        while (newHead != toNode) {
            Node next = newHead.next;
            newHead.next = pre;
            pre = newHead;
            newHead = next;
        }
        // 将要反转的最后一个节点Node(6)和fromNode(2)链接 2->6
        fromNode.next = pre;
        return pre;
    }

    /**
     * 数组反转
     */
    private static void reverseArray(int[] array, int start) {
        if (start < 0 || start >= array.length - 1) {
            return;
        }
        int i = start;
        int j = array.length - 1;
        while (j > i) {
            int temp = array[i];
            array[i] = array[j];
            array[i] = temp;
            i++;
            j--;
        }
    }

    /**
     * 反转字符串
     *
     * @param str
     * @return
     */
    private static String reverseString(String str) {
        String[] seq = str.split(" ");
        int start = 0;
        int end = seq.length - 1;
        while (start < end) {
            String temp = seq[start];
            seq[start] = seq[end];
            seq[end] = temp;
            start++;
            end--;
        }

        StringBuilder builder = new StringBuilder();
        for (String s : seq) {
            builder.append(s).append(" ");
        }
        return builder.toString().trim();
    }
}
