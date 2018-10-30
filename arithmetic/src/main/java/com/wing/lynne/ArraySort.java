package com.wing.lynne;

import java.util.Arrays;


public class ArraySort {

    private static int[] array = {20,11,110,4, 6,9,77, 2,6, 55,3, 4, 5,200};


    /**
     *  1.数组切分的时候，生成的新数据长度需要指定为原长度-1，注意俩个都需要指定该长度
     *  2.数组元素聚合的时候，要先把标兵的值拿出来，因为聚合过程中，可能会被覆盖
     */
    private static int[] quickSort(int[] array) {

        if (array == null || array.length == 0 || array.length == 1) {
            return array;
        }

        int length = array.length;

        int flag = length / 2;
        int flagNumber = array[flag];

        int[] biggerArray = new int[length - 1];
        int biggerArrayIndex = 0;
        int[] smallerArray = new int[length - 1];
        int smallerArrayIndex = 0;

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

        biggerArray = Arrays.copyOfRange(biggerArray, 0, biggerArrayIndex);
        smallerArray = Arrays.copyOfRange(smallerArray, 0, smallerArrayIndex);

        if (biggerArray.length != 0) {
            quickSort(biggerArray);
        }

        if (smallerArray.length != 0) {
            quickSort(smallerArray);
        }

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
