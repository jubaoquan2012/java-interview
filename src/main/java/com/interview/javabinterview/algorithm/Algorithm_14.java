package com.interview.javabinterview.a_algorithm;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * @author: heshineng
 * @createdBy: 2019/11/23 15:07
 */
public class Algorithm_14 {
    /**
     *  题目： 字符串排序
     *       输入一个字符串,按字典序打印出该字符串中字符的所有排列。
     *       例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
     *
     *       输入描述:输入一个字符串,长度不超过9(可能有字符重复),字符只包括大小写字母。
     *
     *    思想过程：
     *
     *       这道题如果不会做，首先从数学层面做出来，然后再转化成程序
     *
     *     数学相关的概率公式相关的公式定义：
     *
     *       1.阶乘
     *         当n>0   n的阶乘= n!= n*(n-1)*n*...*2*1  阶乘表示 从 1 一直乘到 n 的乘积 代表n的阶乘
     *         当n=0   n的阶乘 =1  (强制规定)
     *
     *       排列公式  使用 A 表示  排列的结果和顺序有关
     *
     *        如 从n(n>0)个不同物体中 取出 m(0<m<=n)个物体， 排成一列 ，有多少种排法？
     *
     *           注： 其中 m 大于0
     *           这个 用 A[m,n] 表示 m表示上标 n表示下标 从n元素取出 m个元素 排列结果？
     *
     *
     *                              n!
     *        计算方法   A[m,n]= —————————————— =  n! /  (n-m) !  其中 A[n,n]= n!/0!=n!
     *                             (n-m)!
     *
     *
     *       组合公式  使用 C 表示  组合结果和顺序无关 ，只 和 不同组合的分类有关
     *
     *          如： 从n(n>0)个不同物体中  不重复的取出 m (0<=m<=n)个元素， 取出的结果不管顺序，有多少堆结果？
     *
     *               注：m可以等于0
     *
     *          组合结果 用 C[m,n]  m表示上标 n表示下标  从n元素取出 m个元素 组合的结果？
     *
     *                                n!
     *          计算方法   C[m,n] = ———————————— = n! /  m!(n-m)!    其中 C[0,n] = 1;
     *                               m!(n-m)!
     *
     *
     *         从上式可以看出 C[m,n]  结果 一般都比 A[m,n] 小。 实际上 C[m,n] 就是从 A[m,n] 选出不同的组合构成
     *
     *                                          n*(n-1)*...*2*1
     *         上式的实际计算方法    A[m,n] = —————————————————————————— = n*(n-1)*...*(n-m+1)
     *                                          (n-m)*(n-m-1)*...*2*1
     *
     *                   n!             A[m,n]        n*(n-1)*...*(n-m+1)
     *         C[m,n]=——————————— = —————————————— = ————————————————————————
     *                 m!(n-m)!          m!            m*(m-1)*...*1
     *
     *         重复元素的数列的全排列公式： 假设 有n个元素  其中重复元素数列为 {a1,a2,...,ai}
     *         其中他们重复个数分别为 {m1,m2,...,mi} 个
     *                                  A[n,n]                             n!
     *         整个数列的全排列方式：——————————————————————————————— = ————————————————————————
     *                                A[m1,m1]A[m2,m2]...A[mi,mi]       m1! m2!...mi!
     *
     *        如一个数列中有 6个元素，其中重复元素 {a1,a2} 分别重复 3,2
     *                            A[6,6]             6!
     *        一共全排列元素 ：—————————————————— = ——————
     *                           A[3,3]A[2,2]       3!2!
     *
     *        思路一（排除法）： 6个元素的全排 A[6,6]  因为有重复元素所以 整个有重复需要除掉
     *               3个重复元素 在全排的种类 A[3,3]  2个重复元素 在全排种类 A[2,2]
     *               所以 A[6,6]/ A[3,3]A[2,2]
     *
     *        思路二（直接排序法） 首先从6个 选出 3个元素 （有一种选出 3个重复元素）
     *                           为C[3,6] ,再在剩下的选出 2个元素 (有一种选出 2个重复元素)
     *                           最后剩下元素全排
     *               所以 C[3,6]C[2,3]A[1,1]
     */

    public static void main(String[] args) {
        Algorithm_14 test = new Algorithm_14();
        System.out.println(JSON.toJSONString(test.permutation("abc")));

    }


    /**
     * 没有解题思路
     * @param str
     * @return
     */

    /**
     * 字符串字典全排序的方法
     *   一种递归法
     *
     *   对于无重复值的情况？
     *
     *     固定第一个字符，递归取得首位后面的各种字符串组合；
     *     再把第一个字符与后面每一个字符交换，并同样递归获得首位后面的字符串组合；
     *
     *     递归的出口，就是只剩一个字符的时候，递归的循环过程，
     *                就是从每个子串的第二个字符开始依次与第一个字符交换，然后继续处理子串。
     *
     *   假如有重复值呢？
     *
     *      由于全排列就是从第一个数字起，每个数分别与它后面的数字交换，
     *      我们先尝试加个这样的判断 —— 如果一个数与后面的数字相同那么这两个数就不交换了。
     *
     *      例如 abb，第一个数与后面两个数交换得bab，bba。
     *         然后对于 abb中第二个数和第三个数相同，就不用交换了。
     *        但是对bab，第二个数和第三个数不 同，则需要交换，得到bba。
     *        由于这里的bba和开始第一个数与第三个数交换的结果相同了，因此这个方法不行。
     *
     *       换种思维，对abb，第一个数a与第二个数b交换得到bab，
     *       然后考虑第一个数与第三个数交换，此时由于第三个数等于第二个数，
     *       所以第一个数就不再用与第三个数交换了。再考虑bab，它的第二个数与第三个数交换可以解决bba。
     *       此时全排列生成完毕！
     *
     */
    public ArrayList<String> permutation(String str) {
        ArrayList<String> list = new ArrayList<String>();
        if (str != null && str.length() > 0) {
            permutationHelper(str.toCharArray(), 0, list);
            Collections.sort(list);
        }
        return list;
    }

    /**
     * 真正开始递归排序的方法
     * array 需要排序的数组
     * startIndex 开始于后面交换的位
     * list 添加进元素为
     */
    public void permutationHelper(char[] chars, int startIndex, ArrayList<String> list) {
        if (startIndex == chars.length - 1) {
            list.add(String.valueOf(chars));
        } else {
            //记录当前位 与后面位可能有重复就不再交换
            Set<Character> charSet = new HashSet<>();
            for (int j = startIndex; j < chars.length; ++j) {
                if (j == startIndex || !charSet.contains(chars[j])) {
                    charSet.add(chars[j]);
                    //先进行交换，再递归这个序列 每次使用都用当前位与后面交换，后面数一样不需要交换
                    swap(chars, startIndex, j);
                    permutationHelper(chars, startIndex + 1, list);
                    //交换回去还要还原，给下一个序列继续做处理用 每次交换都会改变原序列，所以要换回来，继续后面循环
                    swap(chars, j, startIndex);
                }
            }
        }
    }

    private void swap(char[] array, int i, int j) {
        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 不需要借助数组的重排序，
     * 直接列出字典的重排序方式，并且不使用递归
     *
     * 思想方式：
     *    一个全排列可看做一个字符串，字符串可有前缀、后缀。
     *    生成给定全排列的下一个排列.所谓一个的下一个就是这一个与下一个之间没有其他的。
     *    这就要求这一个与下一个有尽可能长的共同前缀，也即变化限制在尽可能短的后缀上。
     *
     *    示例：
     *
     *       [例]839647521是1--9的排列。1—9的排列最前面的是123456789，最后面的987654321，
     *      从右向左扫描若都是增的，就到了987654321，也就没有下一个了。否则找出第一次出现下降的位置。
     *
     *   【例】 如何得到346987521的下一个
     *
     *     1，从尾部往前找第一个P(i-1) < P(i)的位置
     *        3 4 6 <- 9 <- 8 <- 7 <- 5 <- 2 <- 1
     *        最终找到6是第一个变小的数字，记录下6的位置i-1
     *
     *     2，从i位置往后找到最后一个大于6的数
     *        3 4 6 -> 9 -> 8 -> 7 5 2 1
     *       最终找到7的位置，记录位置为m
     *
     *     3，交换位置i-1和m的值
     *        3 4 7 9 8 6 5 2 1
     *
     *     4，倒序i位置后的所有数据
     *        3 4 7 1 2 5 6 8 9
     *        则347125689为346987521的下一个排列
     */

    public ArrayList<String> permutation2(String str) {
        ArrayList<String> list = new ArrayList<String>();
        if (str == null || str.length() == 0) {
            return list;
        }
        char[] chars = str.toCharArray();
        //首先将字符串字符按字典排好，才能找确定找下一个序列
        Arrays.sort(chars);
        //此时序列已经 最小序列 递增 如 abc 加入序列
        list.add(String.valueOf(chars));
        int len = chars.length;
        for (; ; ) {
            //定义一个从 后往前找的指针
            int lastIndex = len - 1;

            //从最后一位开始 找倒数的  第一个 pre-1 > pre 的数的位置
            for (; lastIndex > 0 && chars[lastIndex - 1] >= chars[lastIndex]; lastIndex--) {
            }
            /**
             * lastIndex==0 说明没有找到 从后往前 前一位大于后一位
             * 说明目前 数组全部反序 已经不用再找了，退出
             */
            if (lastIndex == 0) {
                break;
            }
            //定义一个 从前往后的指针，此时 preIndex = lastIndex 从后往前 第一个小于前面数的位置
            int preIndex = lastIndex;
            //从 后往前 第一个 前一个比后一个数大的位置开始 从前往后找 第一个 前一个大于等于 后一个的位置
            for (; preIndex < len && chars[preIndex] > chars[preIndex - 1]; preIndex++) {
                //满足条件 说明满足升序
            }
            //交换 他们2者的前一个 位置
            swap(chars, lastIndex - 1, preIndex - 1);
            reverse(chars, lastIndex);

            list.add(String.valueOf(chars));
        }

        return list;
    }


    // 反序
    private void reverse(char[] chars,int k){
        if(chars==null || chars.length<=k)
            return;
        int len = chars.length;
        for(int i=0;i<(len-k)/2;i++){
            int m = k+i;
            int n = len-1-i;
            if(m<=n){
                swap(chars,m,n);
            }
        }

    }
}
