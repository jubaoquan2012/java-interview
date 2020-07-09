package com.interview.javabinterview.algorithm;

import com.interview.javabinterview.algorithm.database.NodeCodeJu;

/**
 * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
 * <p>
 * 示例:
 * <p>
 * 输入:
 * [
 * 1->4->5,
 * 1->3->4,
 * 2->6
 * ]
 * 输出: 1->1->2->3->4->4->5->6
 *
 * @author Ju Baoquan
 * Created at  2020/6/9
 */
public class Algorithm_21 {

    public static void main(String[] args) {
        Algorithm_21 demo = new Algorithm_21();
        demo.method_1();//链表删除:给定单向链表中一个任意节点(非尾节点), 删除此节点
        demo.method_2();//链表合并:合并2个有序链表, 按升序排列
        demo.method_3();//链表合并:合并k个有序链表, 按升序排列
        demo.method_4();//链表反转:单向链表反转
        demo.method_5();//链表反转:单向链表反转,局部反转
    }

    private void method_5() {

    }

    /**
     * 链表反转:单向链表反转
     */
    private void method_4() {
        NodeCodeJu node = AlgorithmUtils.arrayToNode(new int[]{1, 2, 3, 45,});
        NodeCodeJu nodeRes = reverseNode(node);
    }

    /**
     * 单向链表反转
     */
    private NodeCodeJu reverseNode(NodeCodeJu node) {
        if (node == null || node.next == null) {
            return node;
        }

        NodeCodeJu pre = null;
        while (node != null) {
            //链表反转: 当前节点的下一个节点反转链接到 pre
            node.next = pre;
            //保存当前节点为下一次循环中的pre.
            pre = node;
            node = node.next;
        }
        return null;
    }

    /**
     * 合并k个链表 , 按asc输出
     */
    private void method_3() {
        NodeCodeJu node_1 = AlgorithmUtils.arrayToNode(new int[]{1, 3, 4});
        NodeCodeJu node_2 = AlgorithmUtils.arrayToNode(new int[]{1, 4, 5});
        NodeCodeJu node_3 = AlgorithmUtils.arrayToNode(new int[]{2, 8});
        NodeCodeJu[] nodes = {node_1, node_2, node_3};
        NodeCodeJu node = mergeKList(nodes);
        AlgorithmUtils.printNode(node);
    }

    /**
     * 合并两个链表,按asc输出
     */
    private void method_2() {
        NodeCodeJu node_1 = AlgorithmUtils.arrayToNode(new int[]{1, 3, 4});
        NodeCodeJu node_2 = AlgorithmUtils.arrayToNode(new int[]{1, 4, 5});
        NodeCodeJu node = mergeTwoList(node_1, node_2);
        AlgorithmUtils.printNode(node);
    }

    /**
     * 删除链表中的某一个节点
     */
    private void method_1() {
        NodeCodeJu node1 = new NodeCodeJu(1);
        NodeCodeJu node2 = new NodeCodeJu(2);
        NodeCodeJu node3 = new NodeCodeJu(3);
        NodeCodeJu node4 = new NodeCodeJu(4);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        //删除节点
        deleteNode(node3);
        AlgorithmUtils.printNode(node1);
    }

    /**
     * 节点删除
     * 因为，我们无法访问我们想要删除的节点 之前 的节点，我们始终不能修改该节点的 next 指针。
     * 相反，我们必须将想要删除的节点的值替换为它后面节点中的值，然后删除它之后的节点。
     */
    private static void deleteNode(NodeCodeJu node) {
        node.value = node.next.value;
        node.next = node.next.next;
    }

    /**
     * 常规方法
     *
     * @param nodes
     * @return
     */
    private NodeCodeJu mergeKList(NodeCodeJu[] nodes) {
        NodeCodeJu res = new NodeCodeJu(0);
        if (nodes == null || nodes.length == 0) {
            return res;
        } else if (nodes.length == 1) {
            res = nodes[0];
        } else if (nodes.length == 2) {
            res = mergeTwoList(nodes[0], nodes[1]);
        } else {
            res = mergeTwoList(nodes[0], nodes[1]);
            for (int i = 2; i < nodes.length; i++) {
                res = mergeTwoList(res, nodes[i]);
            }
        }
        return res;
    }

    /**
     * [
     * 1->4->5,
     * 1->3->4
     * ]
     */
    private NodeCodeJu mergeTwoList(NodeCodeJu node1, NodeCodeJu node2) {
        NodeCodeJu res = new NodeCodeJu(0);
        NodeCodeJu temp = res;
        if (node1 == null || node2 == null) {
            return null;
        }
        while (node1 != null && node2 != null) {
            if (node1.value < node2.value) {
                temp.next = node1;  //链接到temp后
                node1 = node1.next; //更新node1,下次循环准备
            } else {
                temp.next = node2;
                node2 = node2.next;
            }
            temp = temp.next;
        }
        //处理 node1 和node2 长度不一致的问题
        if (node1 != null) {
            temp.next = node1;
        } else {
            temp.next = node2;
        }
        return res.next;
    }
}
