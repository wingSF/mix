package com.wing.lynne.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 判断数组中存在重复
 * <p>
 * https://leetcode-cn.com/explore/featured/card/top-interview-questions-easy/1/array/24/
 */
public class ArrayContainsDuplicate {

    public static void main(String[] args) {

        int[] array = {3, 1000, 2, 2024, 4, 5, 6, 7, 8, 9, 1};
        System.out.println(containsDuplicate(array));
    }

    public static boolean containsDuplicate(int[] nums) {

        if (nums == null || nums.length <= 1) {
            return false;
        }

        Map<Integer, Integer> countMap = new HashMap<>(nums.length);

        for (int number : nums) {

            if (countMap.containsKey(number)) {
                return true;
            } else {
                countMap.put(number, 1);
            }

        }

        return false;
    }
}
