package com.zcswl.leecode;


/**
 * 字符串相加
 *  给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和。
 *  https://leetcode-cn.com/problems/add-strings/
 * @author zhoucg
 * @date 2021-04-14 13:10
 */
public class AddStringsDemo {


    /**
     * 很牛的算法，讲道理，牛啊
     */
    public String addStrings(String num1, String num2) {
        StringBuilder res = new StringBuilder("");
        int i = num1.length() -1;
        int j = num2.length() -1;
        int carry = 0;
        while (i >= 0 || j >= 0) {
            // 这个牛 '2' - '0' == int(2)
            int n1 = i >= 0 ? num1.charAt(i) - '0' : 0;
            int n2 = j >= 0 ? num2.charAt(j) - '0' : 0;
            int tmp = n1 + n2 +carry;
            carry = tmp / 10;
            res.append(tmp % 10);
            i--;
            j--;
        }
        // 反转操作
        if (carry == 1) {
            res.append(1);
        }
        return res.reverse().toString();
    }

}
