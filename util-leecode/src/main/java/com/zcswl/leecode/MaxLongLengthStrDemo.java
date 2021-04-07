package com.zcswl.leecode;

import java.util.HashMap;
import java.util.Map;

/**
 * 找出java字符串中最大不重复的字串长度
 * @author zhoucg
 * @date 2021-04-01 17:00
 */
public class MaxLongLengthStrDemo {

    /**
     * abcdefgabsadfasdfabcesfsljloll
     * abcdefg
     * bcdefg
     */
    public static void main(String[] args) {
        int length = lengthOfLongestSubstring("abcdefgabsadfasdfabcesfsljloll");
        System.out.println(length);
    }

    private static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int left = 0;
        int max = 1;
        for (int i = 0; i < s.length() ; i++) {
            if (map.containsKey(s.charAt(i))) {
                // 滑动串口，起始位置为最左位置 + 1
                left = Math.max(left, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i - left + 1);
        }
        return max;
    }


}
