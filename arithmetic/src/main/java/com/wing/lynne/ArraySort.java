package com.wing.lynne;

import java.util.Arrays;


public class ArraySort {

    private static int[] array = {20,11,110,4, 6,9,77, 2,6, 55,3, 4, 5,200};


    /**
     *  1.数组切分的时候，生成的新数据长度需要指定为原长度-1，注意俩个都需要指定该长度
     *  2.数组元素聚合的时候，要先把标兵的值拿出来，因为聚合过程中，可能会被覆盖
     */
    private static int[] quickSort(int[] array) {

        //1.特殊数组的处理
        if (array == null || array.length == 0 || array.length == 1) {
            return array;
        }

        //2.获取分段中间索引以及索引位的数值
        int length = array.length;
        int flag = length / 2;
        int flagNumber = array[flag];

        //3.初始化新数组存放截断产生的新数组
        int[] biggerArray = new int[length - 1];
        int biggerArrayIndex = 0;
        int[] smallerArray = new int[length - 1];
        int smallerArrayIndex = 0;

        //4.遍历旧数组，给新初始化的数组赋值
        for (int i = 0; i < length; i++) {
            if (i == flag) {
                continue;
            }

            if (array[i] > array[flag]) {
                biggerArray[biggerArrayIndex] = array[i];
                biggerArrayIndex++;
            } else {
                smallerArray[smallerArrayIndex] = array[i];
                smallerArrayIndex++;
            }
        }

        //5.对新产生的数组进行截取，将0位全部丢弃
        biggerArray = Arrays.copyOfRange(biggerArray, 0, biggerArrayIndex);
        smallerArray = Arrays.copyOfRange(smallerArray, 0, smallerArrayIndex);

        //6.对新产生的数组进行递归处理
        if (biggerArray.length != 0) {
            quickSort(biggerArray);
        }
        if (smallerArray.length != 0) {
            quickSort(smallerArray);
        }

        //7.对老数据重新进行赋值操作
        for (int i = 0; i < smallerArray.length; i++) {
            array[i] = smallerArray[i];
        }
        array[smallerArray.length] = flagNumber;
        for (int i = 0; i < biggerArray.length; i++) {
            array[i + smallerArray.length + 1] = biggerArray[i];
        }

        return array;
    }

    public static void main(String[] args) {

        int[] sortedResult = quickSort(array);

        System.out.println("-------------");
        for (int element : sortedResult) {
            System.out.print(element + "-");
        }
        System.out.println();
        System.out.println("-------------");
    }

}
