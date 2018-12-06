package com.common.util.sort;

/**
 *
 * @author zhoucg
 * @date 2018-12-06
 * 排序算法的定义：
 * 对一序列对象根据某个关键字进行排序
 * 术语介绍：
 *      稳定：如果a原本在b前面，而a=b，排序之后a仍然在b的前面；
 *      不稳定：如果a原本在b的前面，而a=b，排序之后a可能会出现在b的后面；
 *      内排序：所有排序操作都在内存中完成；
 *      外排序：由于数据太大，因此把数据放在磁盘中，而排序通过磁盘和内存的数据传输才能进行；
 *      时间复杂度： 一个算法执行所耗费的时间。
 *      空间复杂度：运行完一个程序所需内存的大小。
 *
 */
public class SortExample {


    public static void main(String[] args) {

    }


    /**
     * 冒泡排序算法：每次都会将对应的最大的值浮动出来
     *
     * @param array
     * @return
     */
    public static int[] bubbleSort(int[] array) {
        if(array.length == 0) {
            return array;
        }
        for(int i=0;i<array.length;i++) {
            for(int j= 0;j<array.length-1-i;j++) {
                if(array[j+1]<array[j]) {
                    int temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                }
            }
        }

        return array;
    }

    /**
     * 选择排序算法：
     *      选择排序(Selection-sort)是一种简单直观的排序算法。它的工作原理：首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，然后，
     *      再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。
     * @param array
     * @return
     */
    public static int[] selectionSort(int[] array) {
        if(array.length == 0) {
            return array;
        }
        for(int i=0;i<array.length ; i++) {
            int minIndex = i;
            for(int j=i;j<array.length;j++) {
                if(array[j]<array[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
        return array;

    }

    /**
     * 插入排序：
     *      插入排序（Insertion-Sort）的算法描述是一种简单直观的排序算法。它的工作原理是通过构建有序序列，对于未排序数据，
     *      在已排序序列中从后向前扫描，找到相应位置并插入。插入排序在实现上，通常采用in-place排序（即只需用到O(1)的额外空间的排序），
     *      因而在从后向前扫描过程中，需要反复把已排序元素逐步向后挪位，为最新元素提供插入空间。
     * @param array
     * @return
     */
    public static int[] insertionSort(int[] array) {
        if(array.length == 0) {
            return  array;
        }
        int current;
        for(int i=0;i<array.length -1 ;i++) {
            current = array[i+1];
            int preIndex = i;
            while(preIndex >= 0 && current < array[preIndex]) {
                array[preIndex + 1] = array[preIndex];
                preIndex--;
            }
            array[preIndex + 1] = current;
        }
        return array;

    }

}
