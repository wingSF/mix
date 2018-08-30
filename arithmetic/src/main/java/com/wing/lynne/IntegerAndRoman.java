package com.wing.lynne;

/**
 * IntegerToRoman   &&   RomanToInteger
 * https://leetcode-cn.com/problems/roman-to-integer/description/
 * https://leetcode-cn.com/problems/integer-to-roman/description/
 */
public class IntegerAndRoman {

    public static void main(String[] args) {

        for (int i = 0; i < 3999; i++) {

            String s = intToRoman(i);
            int j = romanToInt(s);

            if (i != j) {
                System.err.println(i);
                break;
            }
        }

    }

    public static String intToRoman(int num) {

        String[] thousandsArray = {"", "M", "MM", "MMM"};
        String[] hundredsArray = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tensArray = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] onesArray = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

        int thousandsNumber = num / 1000;
        int hundredsNumber = (num - thousandsNumber * 1000) / 100;
        int tensNumber = (num - thousandsNumber * 1000 - hundredsNumber * 100) / 10;
        int onesNumber = (num - thousandsNumber * 1000 - hundredsNumber * 100) % 10;

        String result = thousandsArray[thousandsNumber] + hundredsArray[hundredsNumber] + tensArray[tensNumber] + onesArray[onesNumber];

//        System.out.println(num + "--" + thousandsNumber + " " + hundredsNumber + " " + tensNumber + " " + onesNumber + "-------" + result);

        return result;
    }


    public static int romanToInt(String s) {

        char[] oriChars = s.toCharArray();

        String charDictory = "IVXLCDM";
        int[] numberDictory = {1, 5, 10, 50, 100, 500, 1000};
        int[] secondNumberDictory = {1, 4, 9, 40, 90, 400, 900};

        int total = 0;

        String splitString = "IXC";

        for (int i = 0; i < s.length(); i++) {

            if (splitString.contains(oriChars[i] + "")) {//是否是特殊字符

                if (i != s.length() - 1) {
                    if (oriChars[i + 1] == oriChars[i]
                            || charDictory.indexOf(oriChars[i + 1]) < charDictory.indexOf(oriChars[i])
                            || (charDictory.indexOf(oriChars[i + 1]) - charDictory.indexOf(oriChars[i]) > 2)) {
                        total += numberDictory[charDictory.indexOf(oriChars[i])];
                    } else {
                        total += secondNumberDictory[charDictory.indexOf(oriChars[i + 1])];
                        i++;
                    }
                } else {
                    total += numberDictory[charDictory.indexOf(oriChars[i])];
                }

            } else {//直接求和
                total += numberDictory[charDictory.indexOf(oriChars[i])];
            }
        }

        return total;
    }

}
