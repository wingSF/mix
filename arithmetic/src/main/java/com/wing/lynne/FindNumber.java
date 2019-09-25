package com.wing.lynne;

/**
 * 该类用来演示，查找一个数组中数
 * 已知
 *      数组中存储的是0-99的100个数字
 *      数组中只有俩个元素出现次数是奇数次，其他均为偶数次
 * 求
 *      尽可能低的时间空间复杂度，找到它
 *
 * 原题(第三题)
 *      https://mp.weixin.qq.com/s/jpnqoBDo9ZbVv6WA1_vEDw
 */
public class FindNumber {

    public static void main(String[] args) {

        System.out.println(3 & 1);
        System.out.println(5 & 1);
        System.out.println(2 & 1);


        int[] arr = {1, 2, 3, 4, 5, 5, 4, 3, 2, 1, 6, 7};

        int length = arr.length;

        int temp1 = 0;


        for (int i = 0; i < length; i++) {

            temp1 = temp1 ^ arr[i];
        }

        int mod = 1;
        while (true) {
            int result = temp1 & mod;
            if (result == mod) {
                break;
            } else {
                mod += 2;
            }
        }

        int temp2 = 0;
        int temp3 = 0;

        for (int i = 0; i < length; i++) {

            if ((arr[i] & mod) == mod) {
                temp2 = temp2 ^ arr[i];
            } else {
                temp3 = temp3 ^ arr[i];
            }

        }

        System.out.println(temp2);
        System.out.println(temp3);
    }
}
