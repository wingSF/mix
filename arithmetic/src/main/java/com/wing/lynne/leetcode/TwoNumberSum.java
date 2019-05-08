package com.wing.lynne.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/explore/interview/card/tencent/221/array-and-strings/894/
 */
public class TwoNumberSum {

    public static void main(String[] args) {

        TwoNumberSum twoNumberSum = new TwoNumberSum();

        int[] nums = {2, 7, 11, 15};
        int target = 9;

        System.out.println(Arrays.toString(twoNumberSum.twoSum(nums, target)));
    }

    public int[] twoSum(int[] nums, int target) {

        int[] result = new int[2];

        Map tempMap = new HashMap();

        for (int i = 0; i < nums.length; i++) {
            tempMap.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {

            if (tempMap.containsKey(target - nums[i])) {

                if ((Integer) tempMap.get(target - nums[i]) == i) {
                    continue;
                }
                result[0] = i;
                result[1] = (Integer) tempMap.get(target - nums[i]);
                break;
            }
        }

        return result;
    }
}
