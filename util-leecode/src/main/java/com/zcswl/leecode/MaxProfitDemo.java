package com.zcswl.leecode;

/**
 * 买卖股票的最佳时机
 * [7,1,5,3,6,4]
 * @author zhoucg
 * @date 2021-04-07 17:02
 */
public class MaxProfitDemo {


    public static void main(String[] args) {
        int[] arr = {7,1,5,3,6,4};

    }


    public int maxProfit(int[] arr) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int i = 0; i < arr.length ; i++) {
            if (arr[i] < minPrice) {
                minPrice = arr[i];
            } else if (arr[i] - minPrice > maxProfit) {
                maxProfit = arr[i] - minPrice;
            }
        }
        return maxProfit;
    }

    /**
     * 股票计算的第二种方法，实际上就是计算差值
     * 7,1,5,3,6,4
     */
    public int maxProfit1(int[] arr) {
        if (arr.length == 0) { // 4 - 2 + 3
            return 0;
        }
        int ans = 0;
        int sum = 0;
        for (int i = 1; i < arr.length ; i++) {
            sum += arr[i] - arr[i-1];
            ans = Math.max(ans, sum); // 4
            if (sum < 0) {
                sum = 0;
            }
        }
        return ans;
    }
}
