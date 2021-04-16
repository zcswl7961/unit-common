package com.zcswl.leecode;


import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author zhoucg
 * @date 2021-04-16 13:20
 */
public class 有效的括号 {

    public static void main(String[] args) {
        String s = "(([]){})";
        boolean valid = isValid(s);
        System.out.println(valid);
    }



    public static boolean isValid(String s) {
        // 真是尴尬，我想成了对称性来了

        // 官方的解题思路
        // 我们遍历给定的字符串 ss。当我们遇到一个左括号时，我们会期望在后续的遍历中，
        // 有一个相同类型的右括号将其闭合。由于后遇到的左括号要先闭合，因此我们可以将这个左括号放入栈顶。
        int n = s.length();
        if (n % 2 == 1) {
            return false;
        }

        Map<Character, Character> pairs = new HashMap<Character, Character>() {{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};
        Deque<Character> stack = new LinkedList<Character>();
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (pairs.containsKey(ch)) {
                if (stack.isEmpty() || stack.peek() != pairs.get(ch)) {
                    return false;
                }
                // 出栈
                stack.pop();
            } else {
                // 入栈
                stack.push(ch);
            }
        }
        return stack.isEmpty();
    }


    // 卧槽，这个是真的秒
    public boolean isValid1(String s) {
        while(s.contains("()")||s.contains("[]")||s.contains("{}")){
            if(s.contains("()")){
                s=s.replace("()","");
            }
            if(s.contains("{}")){
                s=s.replace("{}","");
            }
            if(s.contains("[]")){
                s=s.replace("[]","");
            }
        }
        return s.length()==0;
    }
}
