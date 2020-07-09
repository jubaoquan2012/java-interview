package com.interview.javabinterview.algorithm;

import java.util.Stack;

/**
 * 字符串操作
 *
 * @author Ju Baoquan
 * Created at  2020/5/12
 */
public class Algorithm_9 {

    public static void main(String[] args) {

        String str = "{()[]}";
        boolean validString = isValidString(str);
        System.out.println(validString);
    }

    /**
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
     * <p>
     * 有效字符串需满足：
     * <p>
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 注意空字符串可被认为是有效字符串。
     */
    public static boolean isValidString(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '[') {
                stack.push(']');
            } else if (c == '{') {
                stack.push('}');
            } else if (stack.empty() || stack.pop() != c) {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
