package com.wing.lynne.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/3sum/
 *
 * 执行用时: 95 ms, 在3Sum的Java提交中击败了55.27% 的用户
 * 内存消耗: 59.9 MB, 在3Sum的Java提交中击败了8.03% 的用户
 * 执行用时: 94 ms, 在3Sum的Java提交中击败了56.87% 的用户
 * 内存消耗: 57.3 MB, 在3Sum的Java提交中击败了9.18% 的用户
 */
public class ThreeNumberSum {

    public static void main(String[] args) {

//        int[] arrays = {-1,0,1,2,-1,-4};
//        int[] arrays = {-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6};
//        int[] arrays = {-1, 0, 1, 2, -1, -4};
//        int[] arrays = {-4, -2, 1, -5, -4, -4, 4, -2, 0, 4, 0, -2, 3, 1, -5, 0};
//        int[] arrays = {-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6};
//        int[] arrays = {1, 1, -2};
//        int[] arrays = {0, 0, 0, 0};
        int[] arrays = {3, 0, -2, -1, 1, 2};
//        int[] arrays = {-1, -2, -3, 4, 1, 3, 0, 3, -2, 1, -2, 2, -1, 1, -5, 4, -3};

        List<List<Integer>> lists = threeSum(arrays);

        System.out.println(lists);


    }

    public static List<List<Integer>> threeSum(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();

        //对数组进行排序  O(logN)
        Arrays.sort(nums);

        //遍历数组，寻找  O(n)
        for (int i = 0; i < nums.length - 1; i++) {

            if (i != 0) {
                if (nums[i] == nums[i - 1]) {
                    continue;
                }
            }

            int start = i + 1;
            int end = nums.length - 1;

            while (start < end) {

                if (nums[start] + nums[end] < -nums[i]) {
                    start++;
                } else if (nums[start] + nums[end] > -nums[i]) {
                    end--;
                } else {
                    List<Integer> singleResult = new ArrayList<>();
                    singleResult.add(nums[i]);
                    singleResult.add(nums[start]);
                    singleResult.add(nums[end]);

                    result.add(singleResult);

                    do {
                        start++;
                    } while (start <= nums.length - 1 && nums[start] == nums[start - 1]);
                }

            }

        }


        return result;
    }
}


/**
 * @author 王龙
 * var threeSum = function(nums) {
 * nums.sort(function(a,b){
 * return a-b;
 * })
 * var target,result=[],len=nums.length;
 * for(var i=0;i<len;i++){
 * if (target===nums[i]){
 * continue;
 * }
 * else{
 * target=nums[i];
 * }
 * var low=i+1,high=len-1;
 * while(low<high){
 * if(nums[low]+nums[high]===-target){
 * result.push([target,nums[low],nums[high]]);
 * do{
 * low++;
 * }while(nums[low]===nums[low-1]);
 * }
 * else if(nums[low]+nums[high]<-target){
 * low++;
 * }
 * else{
 * high--;
 * }
 * }
 * }
 * return result;
 * };
 */