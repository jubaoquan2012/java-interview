package com.interview.javabinterview.algorithm.byte_dance;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 二叉树遍历:
 * 前提:
 * (1).前中后对应的root节点
 * (2).先左后右
 * <p>
 * 前序遍历: 根,左,右
 * 中序遍历: 左,跟,右
 * 后序遍历: 左,右,跟
 *
 * @author Ju Baoquan
 * Created at  2020/4/8
 */
public class BinaryTree {

    private static TreeNode[] node;

    /**
     * ----------------------0---------------------------
     * -------1--------------------------2-------------
     * ---3--------4--------------5--------------6-----
     * 7-----8--9-----null--null-----null--null-----null
     */
    private static void initTreeNode() {
        //以数组形式生成一棵完全二叉树
        node = new TreeNode[10];
        for (int i = 0; i < 10; i++) {
            node[i] = new TreeNode(i);
        }
        for (int i = 0; i < 10; i++) {
            if (i * 2 + 1 < 10) {
                node[i].left = node[i * 2 + 1];
            }
            if (i * 2 + 2 < 10) {
                node[i].right = node[i * 2 + 2];
            }
        }
    }

    public static void main(String[] args) {
        initTreeNode();
        List<Integer> preList = new ArrayList<>();
        List<Integer> midList = new ArrayList<>();
        List<Integer> postList = new ArrayList<>();
        //前序遍历
        // preOrderRe(node[0], preList);
        preOrder(node[0], preList);
        //中序遍历
        midOrderRe(node[0], midList);

        postOrderRe(node[0], postList);
        //后序遍历
        System.out.println(JSON.toJSONString(preList));
        System.out.println("--------");
        System.out.println(JSON.toJSONString(midList));
        System.out.println("--------");
        System.out.println(JSON.toJSONString(postList));
    }

    /**
     * 递归实现
     * 前序遍历:根,左,右
     */
    private static void preOrderRe(TreeNode treeNode, List<Integer> list) {
        if (treeNode == null) {
            return;
        }
        //根
        list.add(treeNode.value);
        //左
        if (treeNode.left != null) {
            preOrderRe(treeNode.left, list);
        }
        //右
        if (treeNode.right != null) {
            preOrderRe(treeNode.right, list);
        }
    }

    /**
     * 非递归实现
     * 前序遍历:根,左,右
     */
    private static void preOrder(TreeNode treeNode, List<Integer> list) {
        if (treeNode == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while (treeNode != null || !stack.isEmpty()) {
            //左边全部入栈(压栈)
            while (treeNode != null) {
                list.add(treeNode.value);
                stack.push(treeNode);
                treeNode = treeNode.left;
            }
            //栈中元素出栈(后进的先出) 最后进的遍历是否有右节点
            if (!stack.isEmpty()) {
                treeNode = stack.pop();
                treeNode = treeNode.right;
            }
        }
    }

    /**
     * 递归实现
     * 中序遍历:左,根,右
     */
    private static void midOrderRe(TreeNode treeNode, List<Integer> list) {
        if (treeNode == null) {
            return;
        }
        if (treeNode.left != null) {
            midOrderRe(treeNode.left, list);
        }
        list.add(treeNode.value);
        if (treeNode.right != null) {
            midOrderRe(treeNode.right, list);
        }
    }

    /**
     * 非递归实现
     * 中序遍历:左,根,右
     */
    private static void midOrder(TreeNode treeNode, List<Integer> list) {
        if (treeNode == null) {
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        while (treeNode != null || !stack.isEmpty()) {
            while (treeNode != null) {
                stack.push(treeNode);
                treeNode = treeNode.left;
            }
            if (!stack.isEmpty()) {
                treeNode = stack.pop();
                list.add(treeNode.value);
                treeNode = treeNode.right;
            }
        }
    }

    /**
     * 递归实现
     * 后序遍历:左,根,右
     */
    private static void postOrderRe(TreeNode treeNode, List<Integer> list) {
        if (treeNode == null) {
            return;
        }
        if (treeNode.left != null) {
            postOrderRe(treeNode.left, list);
        }
        if (treeNode.right != null) {
            postOrderRe(treeNode.right, list);
        }
        list.add(treeNode.value);
    }

    /**
     * 非递归实现
     * 后序遍历:左,根,右
     */
    private static void postOrder(TreeNode treeNode, List<Integer> list) {
        if (treeNode == null) {
            return;
        }

    }
}
