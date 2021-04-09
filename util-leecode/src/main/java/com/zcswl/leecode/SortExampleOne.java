package com.zcswl.leecode;

/**
 * @author zhoucg
 * @date 2021-04-08 8:55
 */
public class SortExampleOne {

    public static void main(String[] args) {
        int[] arr = {1,11,12,4,7,5,78,19,54,54};
        //int[] ints = selectSort(arr);
        int[] ints = insertSort(arr);
        for (int arr1 : ints) {
            System.out.println(arr1);
        }
    }


    /**
     * 冒泡排序的时间复杂度  O（n2）
     */
    private static int[] maopao(int[] arr) {
        for (int i = 0; i < arr.length ; i++) {
            for (int j = 0; j < arr.length - 1 -i ;j++) {
                /**
                 * 7    14  15  2  11
                 * 14   7   15 2   11
                 * 14   15  7  2  11
                 * 14   15  7  2  11
                 * 14   15  7  11  2
                 * 0 1 2 3 4
                 */
                if (arr[j] < arr[j+1]) {
                    int tmp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        return arr;
    }

    /**
     * 选择排序
     * 15,11,12,4,7,5,78,19,54,54
     * i = 0
     * selectIndex = i 1
     * 每一趟选择最小的值，然后和初始的位置进行排序
     *
     */
    private static int[] selectSort(int[] arr) {
        for (int i = 0; i < arr.length ; i++) {
            int selectIndex = i;
            for (int j = selectIndex +1; j < arr.length ; j++) {
                if (arr[j] < arr[selectIndex]) {
                    selectIndex = j;
                }
            }
            int tmp = arr[selectIndex];
            arr[selectIndex] = arr[i];
            arr[i] = tmp;
        }
        return arr;
    }

    /**
     * 插入排序，以某一个指针线为准，排序之前的是对应的顺序的
     */
    private static int[] insertSort(int[] arr) {
        int size = arr.length;
        for (int i = 0;i < arr.length ;i++) {
            for( int j = i ; j > 0 ; j -- ) {
                if (arr[j] > arr[j-1]) {
                    swap(arr, j ,j-1);
                } else {
                    break;
                }
            }
        }
        return arr;
    }

    private static void swap(int[] arr , int x , int y) {
        int tmp = arr[x];
        arr[x] = arr[y];
        arr[y] = tmp;
    }
}
