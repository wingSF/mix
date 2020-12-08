package com.wing.lynne;

import java.util.BitSet;

/**
 * 给定一个1到N的正整数序列，其中有俩个数字重复，同时缺少一个数字
 *
 * 写一个函数找到重复的数字 和 缺少的数字
 *
 * ex:
 *  input:  [1,5,2,2,3]
 *  output: [2,4]
 */
public class FindDupAndExists {

    public static void main(String[] args) {

        int[] arr = {1, 5, 2, 2, 4};

        findDupAndExists(arr);
    }

    private static int[] findDupAndExists(int[] arr) {

        int[] result = new int[2];

        //创建俩个BitSet，一个存放现有数据，一个存放预期数据
        BitSet currentBitSet = new BitSet(arr.length);

        for (int i = 0; i < arr.length; i++) {

            //放入现有数据时做放判断
            boolean value = currentBitSet.get(arr[i]);
            if (value == false) {
                currentBitSet.set(arr[i]);
            } else {
                //完成重复数据赋值
                result[0]= arr[i];
            }
        }

        //完成缺失数据赋值
        result[1]= currentBitSet.nextClearBit(1);


        return result;

    }
}
