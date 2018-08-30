package com.wing.lynne;

/**
 * 俩个数组，交换其中的某一个元素，使数组中元素的和相等
 * https://leetcode-cn.com/submissions/detail/5850281/
 */
public class SwapArrayElement {

    public static void main(String[] args) {
        int[] a = {1, 2, 5};
        int[] b = {2, 4};
        int[] ints = fairCandySwap(a, b);
        System.out.println(ints[0] + " " + ints[1]);
    }

    public static int[] fairCandySwap(int[] A, int[] B) {

        int sumA = 0;
        int sumB = 0;

        int[] result = new int[2];
        int dValue = 0;

        for (int a : A) {
            sumA += a;
        }

        for (int b : B) {
            sumB += b;
        }

        int avg = (sumA + sumB) / 2;

        int aDValue = avg - sumA;
        int bDValue = avg - sumB;

        if (aDValue > bDValue) {
            dValue = aDValue;

            b:
            for (int a : A) {

                for (int b : B) {

                    if (b == a + dValue) {
                        result[0] = a;
                        result[1] = b;
                        break b;
                    }

                }

            }

        } else {
            dValue = bDValue;

            b:
            for (int b : B) {

                for (int a : A) {

                    if (a == b + dValue) {
                        result[0] = a;
                        result[1] = b;
                        break b;
                    }

                }

            }
        }

        return result;
    }

}
