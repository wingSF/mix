package com.wing.lynne.leetcode;

/**
 * https://leetcode-cn.com/problems/integer-to-english-words/
 */
public class IntegerToEnglishWords {

    private static String[] constant = new String[100];

    static {
        constant[0] = "Zero";
        constant[1] = "One ";
        constant[2] = "Two ";
        constant[3] = "Three ";
        constant[4] = "Four ";
        constant[5] = "Five ";
        constant[6] = "Six ";
        constant[7] = "Seven ";
        constant[8] = "Eight ";
        constant[9] = "Nine ";
        constant[10] = "Ten ";
        constant[11] = "Eleven ";
        constant[12] = "Twelve ";
        constant[13] = "Thirteen ";
        constant[14] = "Fourteen ";
        constant[15] = "Fifteen ";
        constant[16] = "Sixteen ";
        constant[17] = "Seventeen ";
        constant[18] = "Eighteen ";
        constant[19] = "Nineteen ";
        constant[20] = "Twenty ";
        constant[30] = "Thirty ";
        constant[40] = "Forty ";
        constant[50] = "Fifty ";
        constant[60] = "Sixty ";
        constant[70] = "Seventy ";
        constant[80] = "Eighty ";
        constant[90] = "Ninety ";
        constant[91] = "Thousand ";
        constant[92] = "Million ";
        constant[93] = "Billion ";
    }

    public static void main(String[] args) {

//        int num = 1234567891;
//        int num = 12;
//        int num = 12345;
//        int num = 110;
//        int num = 20;
        int num = 1000000;

        String result = numberToWords(num);

        System.out.println(result);
    }

    public static String numberToWord(int num) {

        if (num == 0) {
            return constant[num];
        }

        StringBuilder stringBuilder = new StringBuilder();

        //判断数字位数是否大于三位，将大于三位的部分进行递归
        int lowerNumber = num % 1000;
        int higherNumber = (num - lowerNumber) / 1000;
        if (higherNumber != 0) {
            stringBuilder.append(numberToWord(higherNumber));
            stringBuilder.append("- ");
        }

        //取三位数的最高位
        int highPosition = lowerNumber / 100;
        //取三位数的低俩位
        int lowerPosition = lowerNumber % 100;
        int hundredNumber = lowerPosition / 100;

        if (highPosition != 0) {
            stringBuilder.append(constant[highPosition]);
            stringBuilder.append("Hundred ");
        }

        //取第十位
        int tenNumber = (lowerPosition) / 10;
        int number = lowerPosition - tenNumber * 10;

        if (tenNumber == 0) {
            if (number != 0) {
                stringBuilder.append(constant[lowerPosition - hundredNumber * 100]);
            }
        } else {

            if (tenNumber == 1) {
                stringBuilder.append(constant[lowerPosition]);
            } else {
                stringBuilder.append(constant[tenNumber * 10]);
                if (number != 0) {
                    stringBuilder.append(constant[lowerPosition - tenNumber * 10]);
                }
            }
        }

        return stringBuilder.toString();

    }


    public static String numberToWords(int num) {

        String result = numberToWord(num);

        String[] tempStringResult = result.split("-");

        if (tempStringResult.length == 4) {
            result = result.replaceFirst("- ", constant[93]).replaceFirst("- ", constant[92]).replaceFirst("- ", constant[91]);
        } else if (tempStringResult.length == 3) {
            result = result.replaceFirst("- ", constant[92]).replaceFirst("- ", constant[91]);
        } else if (tempStringResult.length == 2) {
            result = result.replaceFirst("- ", constant[91]);
        }

        if (result.indexOf(constant[92] + constant[91]) != -1) {
            result = result.replace(constant[91], "");
        }

        if (result.indexOf(constant[93] + constant[92]) != -1) {
            result = result.replace(constant[92], "");
        }

        return result.trim();
    }
}

/**
 * 核心思想
 *      int的最大值 2147483647，按照歪果仁的计数方式 2 147 483 647，分为三部分，用billion million thousand分开
 *      核心方法实现1000以下数字的转化
 *      每段转换完成后拼接单位
 * 边界情况
 *      单独为0的时候，输出zero
 *      高位有数字的时候，zero不输出
 *      当数字小于19的时候，即0-19有单独的表示word，当大于等于20的时候，采用十位数组和各位数字单词连接的方式
 *      当连续俩个三位均为0 ，即出现 1 000 000的时候，要注意只保留最后一个单位
 */
