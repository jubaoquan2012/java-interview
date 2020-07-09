package com.interview.javabinterview.algorithm;

import com.interview.javabinterview.algorithm.database.DoubleNodeCodeJu;
import com.interview.javabinterview.algorithm.database.NodeCodeJu;
import com.interview.javabinterview.java_base.map.ArrayList;

import java.util.List;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/7
 */
public class AlgorithmUtils {

    public static DoubleNodeCodeJu arrayToDoubleNode(int[] array) {
        if (array.length == 0) {
            return null;
        }
        DoubleNodeCodeJu node = new DoubleNodeCodeJu(0);
        DoubleNodeCodeJu temp = node;
        for (int value : array) {
            temp.next = new DoubleNodeCodeJu(temp,value);
            temp = temp.next;
        }
        return node.next;
    }

    /**
     * 数组转换为链表
     */
    public static NodeCodeJu arrayToNode(int[] array) {
        if (array.length == 0) {
            return null;
        }
        NodeCodeJu node = new NodeCodeJu(0);
        NodeCodeJu temp = node;
        for (int value : array) {
            temp.next = new NodeCodeJu(value);
            temp = temp.next;
        }
        return node.next;
    }

    public static void printNode(NodeCodeJu node) {
        StringBuilder sb = new StringBuilder("节点输出:[");
        while (node != null) {
            sb.append(node.value);
            node = node.next;
            if (node != null) {
                sb.append(",");
            }
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    public static void print(int[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(",");
            }
        }
        sb.append("}");
        System.out.println(sb.toString());
    }

    /**
     * 两个位置交换
     *
     * @param array
     * @param a
     * @param b
     */
    public static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    /**
     * 当前位置和下一个位置交换
     */
    public static void swapNext(int[] array, int currentIndex) {
        int temp = array[currentIndex];
        array[currentIndex] = array[currentIndex + 1];
        array[currentIndex + 1] = temp;
    }

    public static int[] reverse(int[] array) {
        int minIndex = 0;
        int maxIndex = array.length - 1;
        while (minIndex < maxIndex) {
            int temp = array[minIndex];
            array[maxIndex] = array[maxIndex];
            array[minIndex] = temp;
            minIndex++;
            maxIndex--;
        }
        return array;
    }

    public static void reverseArray(int[] array, int startIndex) {
        int i = startIndex;
        int j = array.length - 1;
        while (i < j) {
            swap(array, i, j);
            i++;
            j--;
        }
    }

    /**
     * 把endIndex 的数插入到beginIndex后面
     * 1 2 3 4 5 6 7 8 9 把 8 插入到 3 的后面 其他的相对位置不变
     * 1 2 3 8 4 5 6 7 9
     *
     * @return
     */
    private static int[] test(int[] array, int beginIndex, int endIndex) {
        //第一步, 把endIndex临时保存
        int temp = array[endIndex];
        //第二步, 从
        for (int i = endIndex; i > beginIndex; i--) {
            array[i] = array[i - 1];
        }
        //第三步,将最后endIndex 放入到beginIndex+1
        array[beginIndex + 1] = temp;
        return array;
    }

    public static int[] parseIntToArray(int num) {
        if (num <= 0) {
            return null;
        }
        List<Integer> list = new ArrayList<>();
        while (num > 0) {
            list.add(num % 10);
            num = num / 10;
        }
        int[] array = new int[list.size()];
        for (int i = list.size() - 1, j = 0; i >= 0; i--, j++) {
            array[j] = list.get(i);
        }
        return array;
    }
}
