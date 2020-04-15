package com.interview.javabinterview.algorithm.byte_dance;

import com.alibaba.fastjson.JSON;
import com.interview.javabinterview.algorithm.AlgorithmUtils;
import com.interview.javabinterview.collection.HashMap;
import com.interview.javabinterview.collection.LinkedList;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/2
 */
public class ByteDanceAlgorithm {
    //https://blog.csdn.net/seagal890/article/details/93924905  求一个数组中正数和负数交替出现的最长子数组（JAVA代码）

    private static int[] array_1 = {26, 45, 59, 14, 18, 21, 17, 18};

    private static int[] array_2 = {1, -1, -2, -3, -4, -5, 2, 3, 4, 5};

    private static int[] pokers = {4, 2, 3, 1};

    private static int[] pokerSort_forward = {1, 2, 3, 4};

    private static String s = "abcbef";

    private static int[] arary = {1, 3, 3, 5, 2, 3, 4};

    private static int[] arary_2 = {1, 2, 3, 4};

    public static void main(String[] args) {
        //System.out.println(reverseString("I am a student."));
        //        quickSort(array_1, 0, array_1.length - 1);
        //        sort(array_1);
        //        oddSort(array_1);
        //        test(array_1, 2, 5);
        //        pnSort(array_2);
        //        AlgorithmUtils.print(array_2);
        //AlgorithmUtils.reverse(array_2);

        //pokerSort(pokers);
        //pokerSort_forward(pokerSort_forward);

        //System.out.println(lengthOfLongestint(s));

        nextPermutation(arary_2);

        System.out.println(JSON.toJSONString(arary_2));
    }

    private static void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }

    private static void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 快速排序
     */
    private static void quickSort(int[] array, int low, int high) {
        if (array.length == 0 || low >= high) {
            return;
        }
        //1.暂定一个分治点
        int pivot = array[low];
        int left = low, right = high;

        //2.根据分治点,分为两个数组:小于pivot一组、大于pivot一组
        while (left < right) {
            while (left < right && array[right] >= pivot) {
                right--;
            }

            while (left < right && array[left] <= pivot) {
                left++;
            }

            if (left < right) {
                int temp = array[right];
                array[right] = array[left];
                array[left] = temp;
            }
        }

        //3.把pivot放在两组组中间位置: low和pivot交换位置
        array[low] = array[left];
        array[left] = pivot;

        //4.迭代两组
        quickSort(array, low, left - 1);
        quickSort(array, left + 1, high);
    }

    /**
     * 奇偶排序
     * 思路:
     * 例如: [6 2 4 1 5 9]
     * 第一次: 比较奇数列 6:2比,4:1比,5:9比 :[2 6 1 4 5 9]
     * 第二次: 比较偶数列 6:1,4:5      :[2 1 6 4 5 9]
     * 第三次: 比较奇数列 2:1,6:4,5:9  :[1 2 4 6 5 9]
     */
    private static int[] oddSort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        boolean exchangeFlag = true;
        int start = 0;
        while (exchangeFlag || start == 1) {
            exchangeFlag = false;
            for (int i = start; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    AlgorithmUtils.swapNext(array, i);
                    exchangeFlag = true;
                }
            }
            if (start == 0) {
                start = 1;
            } else {
                start = 0;
            }
        }
        return array;
    }

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

    private static int removeDuplicates(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int slow = 0;
        for (int fast = 1; fast < array.length; fast++) {
            if (array[slow] != array[fast]) {
                slow++;
                array[slow] = array[fast];
            }
        }
        return slow + 1;
    }

    /**
     * 给定一个数组，数组中有正数和负数
     * 1.正负数交替排列
     * 2.保证各自的相对顺序不变
     */
    private static int[] pnSort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        int slow = 0;
        while (slow < array.length - 1) {
            if (array[slow] * array[slow + 1] > 0) {
                int index = 0;
                for (int fast = slow + 1; fast < array.length; fast++) {
                    if (array[slow] * array[fast] < 0) {
                        index = fast;
                        break;
                    }
                }
                int temp = array[index];
                for (int i = index; i >= slow + 1; i--) {
                    array[i] = array[i - 1];
                }
                array[slow + 1] = temp;
            }
            slow++;
        }
        return array;
    }

    /**
     * 1,2,3,4
     * 手中有一堆扑克牌， 但是观众不知道它的顺序。
     * 第一步， 我从牌顶拿出一张牌， 放到桌子上。
     * 第二步， 我从牌顶再拿一张牌， 放在手上牌的底部。
     * 第三步， 重复第一/二步的操作， 直到我手中所有的牌都放到了桌子上。
     * 最后观众看到的是 4 2 3 1
     *
     * @param pokers
     */
    private static void pokerSort_forward(int[] pokers) {
        if (pokers.length == 0) {
            return;
        }
        LinkedList<Integer> handlePokers = new LinkedList<>();
        for (int poker : pokers) {
            handlePokers.add(poker);
        }

        LinkedList<Integer> deskPokers = new LinkedList<>();
        while (handlePokers.size() > 0) {
            //先哪一张放入桌子上
            deskPokers.addFirst(handlePokers.pollFirst());
            //然后把手里的牌 first 放入到 last
            if (handlePokers.size() > 1) {
                handlePokers.addLast(handlePokers.pollFirst());
            }
        }
        deskPokers.forEach(System.out::println);
    }

    /**
     * 1,2,3,4
     * 手中有一堆扑克牌， 但是观众不知道它的顺序。
     * 第一步， 我从牌顶拿出一张牌， 放到桌子上。
     * 第二步， 我从牌顶再拿一张牌， 放在手上牌的底部。
     * 第三步， 重复第一/二步的操作， 直到我手中所有的牌都放到了桌子上。
     * 最后观众看到的是 4 2 3 1
     * <p>
     * 反推手中的顺序
     *
     * @param pokers
     */
    private static void pokerSort(int[] pokers) {
        if (pokers.length == 0) {
            return;
        }
        //先初始化桌子上的牌
        LinkedList<Integer> deskList = new LinkedList<>();
        for (int poker : pokers) {
            deskList.add(poker);
        }
        LinkedList<Integer> handList = new LinkedList<>();
        for (Integer poker : deskList) {
            //判断手里是否有牌, 有牌反向操作:把最后一张牌放到顶部
            if (handList.size() > 1) {
                handList.addFirst(handList.pollLast());
            }
            handList.addFirst(poker);
        }
        handList.forEach(System.out::println);
    }

    private static int lengthOfLongestSubstring(String s) {
        if (s == null || "".equals(s)) {
            return 0;
        }
        LinkedList<Character> list = new LinkedList<>();
        for (Character character : s.toCharArray()) {
            if (!list.contains(character)) {
                list.add(character);
            }
        }
        return list.size();
    }

    /**
     * abcbef
     *
     * @param s
     * @return
     */
    private static int lengthOfLongestint(String s) {
        if (s == null || "".equals(s)) {
            return 0;
        }
        int ans = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int end = 0, start = 0; end < s.length(); end++) {
            char c = s.charAt(end);
            if (map.containsKey(c)) {
                start = Math.max(map.get(c), start);
            }
            ans = Math.max(ans, end - start + 1);
            map.put(c, end + 1);
        }
        return ans;
    }

    /**
     * 递归实现
     *
     * @param n
     * @return
     */
    private static int FibonacciSeq_1(int n) {
        if (n <= 0) {
            return -1;
        }
        if (n <= 2) {
            return 1;
        } else {
            return FibonacciSeq_1(n - 1) + FibonacciSeq_1(n - 2);
        }
    }

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

    public Node reverseList(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node pre = null;//当前结点的前结点
        Node next = null;//当前结点的后结点
        while (head != null) {//从左到右，依次把->变为<-
            next = head.next;
            head.next = pre;//当前结点指向前面的结点
            pre = head;//pre结点右移
            head = next;//head结点右移
        }
        return pre;
    }

    //反转双向链表
    public DoubleNode reverseList(DoubleNode head) {
        DoubleNode pre = null;
        DoubleNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;//->改为<-
            head.pre = next;//<-改为->
            pre = head;//pre右移
            head = next;//head右移
        }
        return pre;
    }

    public class Node {

        int value;

        Node next;
    }

    public class DoubleNode {

        int value;

        DoubleNode pre;

        DoubleNode next;
    }
}

