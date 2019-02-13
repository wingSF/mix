package com.wing.lynne.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeNumberSum {

    public static void main(String[] args) {

//        int[] arrays = {-1,0,1,2,-1,-4};
//        int[] arrays = {-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6};
//        int[] arrays = {-1, 0, 1, 2, -1, -4};
//        int[] arrays = {-4, -2, 1, -5, -4, -4, 4, -2, 0, 4, 0, -2, 3, 1, -5, 0};
//        int[] arrays = {-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6};
//        int[] arrays = {1, 1, -2};
//        int[] arrays = {0, 0, 0, 0};
        int[] arrays = {3,0,-2,-1,1,2};
//        int[] arrays = {-1, -2, -3, 4, 1, 3, 0, 3, -2, 1, -2, 2, -1, 1, -5, 4, -3};

        List<List<Integer>> lists = threeSum(arrays);

        System.out.println(lists);


    }

    public static List<List<Integer>> threeSum(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();

        //对数组进行排序  O(logN)
        Arrays.sort(nums);

        //遍历数组，寻找0的位置  O(n)
        for (int i = 0; i < nums.length - 1; i++) {

            //去除重复
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            //遍历的过程中寻找和为0的组合 todo 当前实现有问题
            int left = i + 1;
            int right = nums.length - 1;
            boolean flag = true;

            while (left != right) {

                if (nums[i] + nums[left] + nums[right] == 0) {

                    List<Integer> resultList = new ArrayList<>();
                    resultList.add(nums[i]);
                    resultList.add(nums[left]);
                    resultList.add(nums[right]);

                    result.add(resultList);

                    while (true) {
                        if (left < right && nums[left] == nums[left + 1] && left + 1 != right) {
                            left++;
                        } else {
                            break;
                        }
                    }

                    left++;
                    flag = false;

                } else {

                    if (flag) {
                        left++;
                        flag = false;
                    } else {
                        right--;
                        flag = true;
                    }
                }

            }

        }


        return result;
    }
}
