package com.wing.lynne.leetcode;

/**
 * https://leetcode-cn.com/problems/find-peak-element/
 * <p>
 * 理解题意很重要啊，寻找峰值，并不是找最大。。。。
 * 1，2，1，3，5，6，4  返回 元素2或者6都可以。。。。
 */
public class FindPeakElement {

    public static void main(String[] args) {

        int[] nums = {1, 8, 1, 3, 5, 6, 4};
        int peakElement = findPeakElement(nums);
        System.out.println(peakElement);
    }

    public static int findPeakElement(int[] nums) {

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = (left + right) / 2;

            if (nums[mid] > nums[mid + 1]) {
                right = mid;
            } else {//已知条件，没有相等元素
                left = mid + 1;
            }
        }

        return left;
    }

}
