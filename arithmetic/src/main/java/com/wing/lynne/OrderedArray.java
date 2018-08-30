package com.wing.lynne;

public class OrderedArray {

    /**
     * 已经一个有序数组。排序情况可能是升序，可能是降序，可能是先降序后升序，求数组中的最小值
     *
     * @param args
     */

    public static void main(String[] args) {

        int[] array1 = {1, 2, 3, 4};
        int[] array2 = {3, 2, 1, 0};
        int[] array3 = {10, 9, 8, 6, 4, 3, 2, 13};

//        System.out.println(findMinMember1(array1));
//        System.out.println(findMinMember1(array2));
//        System.out.println(findMinMember1(array3));

        System.out.println(findMinMember2(array1));
        System.out.println(findMinMember2(array2));
        System.out.println(findMinMember2(array3));


    }

    private static int findMinMember1(int[] array) {

        if (array == null || array.length == 0) {
            throw new IllegalArgumentException();
        }

        if (array.length == 1) {
            return array[0];
        }

        if (array.length == 2) {
            return array[0] < array[1] ? array[0] : array[1];
        }

        int leftLag = 0;
        int rightFlag = array.length - 1;

        if (array[leftLag] < array[rightFlag]) {

            int minValue = array[leftLag];

            while (leftLag != rightFlag) {

                if (array[leftLag + 1] < array[leftLag]) {
                    minValue = array[leftLag + 1];
                    leftLag++;
                } else {
                    return minValue;
                }

                if (array[rightFlag - 1] < array[rightFlag]) {
                    rightFlag--;
                }

            }

            return minValue;
        } else {

            int minValue = array[rightFlag];

            while (leftLag != rightFlag) {

                if (array[rightFlag - 1] < array[rightFlag]) {
                    minValue = array[rightFlag - 1];
                    rightFlag--;
                } else {
                    return minValue;
                }

                if (array[leftLag + 1] < array[rightFlag]) {
                    rightFlag++;
                }

            }

            return minValue;
        }
    }

    private static int findMinMember2(int[] array) {

        if (array == null || array.length == 0) {
            throw new IllegalArgumentException();
        }

        if (array.length == 1) {
            return array[0];
        }

        if (array.length == 2) {
            return array[0] < array[1] ? array[0] : array[1];
        }

        int leftFlag = 0;
        int rightFlag = array.length - 1;

        while (leftFlag < rightFlag) {

            if ((leftFlag + rightFlag) % 2 == 1) {

                int midIndex = (leftFlag + rightFlag) / 2;

                if (midIndex == 0) {
                    return array[midIndex];
                }

                if (array[midIndex - 1] > array[midIndex] && array[midIndex] > array[midIndex + 1]) {
                    leftFlag = midIndex + 1;
                } else if (array[midIndex - 1] < array[midIndex] && array[midIndex] < array[midIndex + 1]) {
                    rightFlag = midIndex - 1;
                } else if (array[midIndex - 1] > array[midIndex] && array[midIndex] < array[midIndex + 1]) {
                    return array[midIndex];
                }

            } else {

                int midLeft = (leftFlag + rightFlag) / 2;

                if (midLeft == array.length - 1) {
                    return array[midLeft];
                }

                int midRight = midLeft + 1;

                if (array[midLeft] > array[midRight]) {
                    leftFlag = midRight;
                }

                if (array[midLeft] < array[midRight]) {
                    rightFlag = midLeft;
                }

            }


        }

        //todo 此处为实现
        return 0;
    }

}
