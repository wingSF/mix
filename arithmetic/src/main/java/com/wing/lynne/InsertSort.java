package com.wing.lynne;

/**
 * 插入排序
 */
public class InsertSort {

    private static int[] array = {20, 11, 110, 4, 6, 9, 77, 2, 6, 55, 3, 4, 5, 200};

    public static void main(String[] args) {

        insertSort(array);

        for (int element : array) {
            System.out.print(element + "-");
        }

    }

    private static void insertSort(int[] array) {

        int length = array.length;

        for (int i = 1; i < length; i++) {
            for (int j = 0; j < i; j++) {
                if (array[i] < array[j]) {
                    array[i] = array[j] + array[i];
                    array[j] = array[i] - array[j];
                    array[i] = array[i] - array[j];
                }
            }

        }

    }

}
