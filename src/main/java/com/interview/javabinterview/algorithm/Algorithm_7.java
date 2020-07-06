package com.interview.javabinterview.a_algorithm;

import com.interview.javabinterview.c_data_structure.LinkedList;

/**
 * 扑克牌游戏
 *
 * @author Ju Baoquan
 * Created at  2020/5/12
 */
public class Algorithm_7 {

    public static void main(String[] args) {
        System.out.println("");
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

}
