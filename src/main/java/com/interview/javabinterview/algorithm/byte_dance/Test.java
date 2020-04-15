package com.interview.javabinterview.algorithm.byte_dance;

import java.util.ArrayList;
import java.util.List;

/**
 * 类
 *
 * @author Ju Baoquan
 * Created at  2020/4/10
 */
public class Test {

    public static void main(String[] args) {
        List<Integer> s = new ArrayList<Integer>();
        s.add(1);
        s.add(2);
        s.add(3);
        s.add(4);
        List<Integer> rs = new ArrayList<Integer>();
        pl(s, rs);
        Integer[] list = (Integer[]) rs.toArray();
    }

    private static void pl(List<Integer> s, List<Integer> rs) {
        if (s.size() == 1) {
            rs.add(s.get(0));
            rs.remove(rs.size() - 1);
        } else {
            for (int i = 0; i < s.size(); i++) {
                rs.add(s.get(i));
                List<Integer> tmp = new ArrayList<Integer>();
                for (Integer a : s) {
                    tmp.add(a);
                }
                tmp.remove(i);
                pl(tmp, rs);
                rs.remove(rs.size() - 1);
            }
        }
    }

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
}
