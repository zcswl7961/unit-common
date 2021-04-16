package com.zcswl.leecode;

/**
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 *
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
 *
 * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-in-rotated-sorted-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author zhoucg
 * @date 2021-04-07 18:13
 */
public class 旋转后的数组找位置 {


    public int search(int[] nums, int target) {
        // 1, 思路：如果中间的数小于最右边的数，则右半段是有序的，若中间数大于最右边数，则左半段是有序的，
        // 我们只要在有序的半段里用首尾两个数组来判断目标值是否在这一区域内，这样就可以确定保留哪半边了
        int len = nums.length;
        int left = 0, right = len - 1;
        while(left <= right){
            int mid = (left + right) / 2;
            if(nums[mid] == target)
                return mid;
            else if(nums[mid] < nums[right]){
                // 如果中间数小于最右数 ，右半升序
                if(nums[mid] < target && target <= nums[right])
                    left = mid+1;
                else
                    right = mid-1;
            }
            else{
                // 如果中间数大于右边，左边升序
                if(nums[left] <= target && target < nums[mid])
                    right = mid-1;
                else
                    left = mid+1;
            }
        }
        return -1;
    }


    /**
     * 自己得思路
     */
    public int search1(int[] nums, int target) {
        // 找到对应得递增得关系
        // 1，如果nums 为null或者是对应得结果为1得话，进行查询
        if (nums == null) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0] == target ? 0 : -1;
        }
        // 找出对应得递增-> 递减得关系
        // 7 8 1 2 3 4 5 6
        int mid = 0;
        int leftNum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > leftNum) {
                mid = i;
                leftNum = nums[i];
            } else {
                break;
            }
        }
        int left = 0 ;
        int right = nums.length - 1;
        while(left < right) {
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[left] == target) {
                return left;
            }
            if (nums[right] == target) {
                return right;
            }
            if (nums[mid] > target && nums[left] < target) {
                // 中间的数大于它，并且最左边得数小于它 向左找
                right = mid -1;
            } else if (nums[mid] > target && nums[left] > target) {
                // 中间得数大于它，并且最左边得数也是大于它，向右找
                left = mid + 1;
            } else if (nums[mid] < target && nums[right] < target) {
                // 中间得数小于它，并且最右边得也是小于它，向左找
                right = mid - 1;

            } else {
                left = mid + 1;
            }
            mid = (left + right + 1  ) / 2;
        }
        return -1;
    }
}
