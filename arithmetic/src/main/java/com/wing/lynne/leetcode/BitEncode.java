package com.wing.lynne.leetcode;

/**
 * https://leetcode-cn.com/problems/1-bit-and-2-bit-characters/
 */
public class BitEncode {

    public static void main(String[] args) {


        int[] bits = {1, 1, 0, 1, 0};
        System.out.println(isOneBitCharacter(bits));
        System.out.println(isOneBitCharacterNo1(bits));

    }

    /**
     * 没有充分使用已知条件
     * 给定的参数总是以0结尾
     *
     * @param bits  入参数组
     * @return 返回是否以0结尾
     */
    private static boolean isOneBitCharacter(int[] bits) {

        int size = bits.length;
        if (size <= 0) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < size; i++) {

            if (bits[i] == 0) {
                continue;
            }

            i++;

            if (i == size - 1) {
                return false;
            }

        }

        return true;
    }

    /**
     * 从尾部开始入手，直接从倒数第二开始找0，或者到头
     *
     * 历经的元素个数为偶数，则返回true，相反为false
     *
     * 个人解读
     *  首先给定的肯定是复合条件的，不会出现无法解析的情况
     *  倒着找到第二个0的时候，那么0一定是前面的解码结束位置
     *
     *  从0开始，到末尾，中间全是1
     *
     *  如果是基数个1，那么一定是 11 11 11 ..... 10 结尾
     *  如果是偶数个1，那么一定是 11 11 11 ..... 0 结尾
     *
     *
     * @param bits 入参数组
     * @return 返回是否以0结尾
     */
    private static boolean isOneBitCharacterNo1(int[] bits) {
        if (bits.length == 1) {
            return bits[0] == 0;
        }
        if (bits[bits.length - 1] != 0) {
            return false;
        }
        if (bits[bits.length - 2] == 0) {
            return true;
        }

        int count1 = 0;
        for (int i = bits.length - 2; i >= 0; i--) {
            if (bits[i] == 0) {
                break;
            }
            count1++;
        }

        return (count1 % 2 == 0);
    }
}
