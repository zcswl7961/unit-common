package com.zcswl.leecode;

/**
 * 买卖股票的最佳时机
 * [7,1,5,3,6,4]
 * @author zhoucg
 * @date 2021-04-07 17:02
 */
public class MaxProfitDemo {


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
}
