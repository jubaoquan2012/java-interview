package com.interview.javabinterview.algorithm.byte_dance;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/2
 */
public class ReverseNodeSimple {

    /**
     * 例如 A->B->C->D
     * <p>
     *  第一个循环 node = A
     * temp = A.next = B
     * A.next=pre(null)
     * pre = A = null
     * node = temp = B
     * <p>
     * 第二次循环  node = B
     * temp = B.next = C
     * B.next=pre(A)=>即完成B->A
     * pre = node = B
     * node = temp = C
     *
     * @param node
     * @return
     */

    public Node reverse(Node node) {
        if (node == null || node.next == null) {
            return node;
        }
        Node pre = null;
        Node temp = null;
        while (node != null) {       // node = B
            temp = node.next;        //把下一个节点临时保存                   temp = B.NEXT = C
            node.next = pre;         //node节点的next是原始的前一个节点:       B.NEXT = A; 完成B->A
            pre = node;              //保存当前节点为pre                     pre = B;
            node = temp;             //当前节点为下次循环的头结点              node = C
        }
        return pre;
    }

    public class Node {
        int value;
        Node next;
    }
}
