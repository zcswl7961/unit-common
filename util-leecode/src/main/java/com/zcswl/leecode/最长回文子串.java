package com.zcswl.leecode;

/**
 * @author zhoucg
 * @date 2021-04-18 21:47
 */
public class 最长回文子串 {


    public static void main(String[] args) {
        String s = "xasababaii";
        System.out.println(longestPalindrome1(s));
    }

    /**
     * 动态规划得算法，如果
     * 对于一个子串而言，如果它是回文串，并且长度大于 2，那么将它首尾的两个字母去除之后，它仍然是个回文串。例如对于字符串 ababa，我们已经知道
     * bab是一个回文子串，那么ababa 一定也是回文子串
     * P（i，j）表示字符串 s 的第 i 到 j 个字母组成的串（下文表示成 s[i:j]）是否为回文串
     * P(i,j) = P(i+1, j-1) 也是一个回文子串
     *
     *  对于长度为1 的字符，显然其本身就是一个回文子串
     *  即：
     *  P(i,i) = true
     *  对于长度为2即
     *  P(i,i+1) = true ,即两个字符相同
     */
    public static String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }

        int maxLen = 1;
        int begin = 0;
        // dp[i][j] 表示 s[i..j] 是否是回文串
        boolean[][] dp = new boolean[len][len];
        // 初始化：所有长度为 1 的子串都是回文串
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }

        char[] charArray = s.toCharArray();
        // 递推开始
        // 先枚举子串长度
        for (int L = 2; L <= len; L++) {
            // ababa
            // 枚举左边界，左边界的上限设置可以宽松一些
            for (int i = 0; i < len; i++) {
                // 由 L 和 i 可以确定右边界，即 j - i + 1 = L 得
                int j = L + i - 1;
                // 如果右边界越界，就可以退出当前循环
                if (j >= len) {
                    break;
                }

                if (charArray[i] != charArray[j]) {
                    dp[i][j] = false;
                } else {
                    if (j - i < 3) {
                        // 这个是关键
                        // j = L +i - 1 - i < 3
                        // j = L < 4
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                // 只要 dp[i][L] == true 成立，就表示子串 s[i..L] 是回文，此时记录回文长度和起始位置
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    // 动态规划解决
    private static String longestPalindrome1(String s) {
        if (s.isEmpty()) {
            return s;
        }
        int n = s.length();
        boolean[][] dp = new boolean[n][n];

        int left = 0;
        int right = 0;

        // x a s a b a b a i i;
        // n = 10
        // i = 8 i
        // n - 2 的分析是考虑了最低aa的情况下
        for (int i = n - 2; i >= 0; i--) {
            dp[i][i] = true;
            for (int j = i + 1; j < n; j++) {
                // 从n-2 的位置，进行迭代
                // 迭代的条件是 dp[i][j] = s.char
                // aba
                // i = 0 j = 2 j - i < 3 || 表示的就是aba的关系
                dp[i][j] = s.charAt(i) == s.charAt(j) &&( j-i<3 || dp[i+1][j-1]);//小于3是因为aba一定是回文
                if(dp[i][j]&&right-left<j-i){
                    // 如果 i ， j 为true的情况下， 如果left小于right
                    left=i;
                    right=j;
                }
            }
        }
        return s.substring(left,right+1); // 左【）
    }

}
