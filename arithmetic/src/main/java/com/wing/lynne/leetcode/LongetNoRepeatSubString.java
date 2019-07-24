package com.wing.lynne.leetcode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 */
public class LongetNoRepeatSubString {

    public static void main(String[] args) {

//        String str = "abcabcbb";
        String str = "abba";
        System.out.println(lengthOfLongestSubstring(str));

    }

    /**
     * 总体思想，空间换时间
     * map用来记录出现过的字母(key)+位置(value)
     * linkedList用来记录进入map的顺序
     * <p>
     * 当出现重复字母的时候，通过当前和历史的对比，获取最新的字串长度
     * 同时要把历史字母之前的记录删除掉
     *
     * @param s 入参字符串
     * @return 返回最长不重复子串的长度
     */
    private static int lengthOfLongestSubstring(String s) {

        Map<Character, Integer> characterMap = new HashMap<>();
        LinkedList<Character> linkedList = new LinkedList<>();

        int number = 0;
        int tempNumber = 0;

        char[] chars = s.toCharArray();

        for (int i = 0; i < chars.length; i++) {

            char c = chars[i];

            if (characterMap.containsKey(c)) {
                if (tempNumber > number) {
                    number = tempNumber;
                }
                Integer oldIndex = characterMap.get(c);
                tempNumber = i - oldIndex;
                //这里要删除比oldIndex小的记录
                while (true) {
                    Character oldC = linkedList.poll();
                    if (!Character.valueOf(c).equals(oldC)) {
                        characterMap.remove(oldC);
                    } else {
                        break;
                    }
                }

            } else {
                tempNumber++;
            }

            characterMap.put(c, i);
            linkedList.offer(c);
        }

        if (tempNumber > number) {
            number = tempNumber;
        }


        return number;
    }

    /**
     * 全网最快的答案
     * 但按照时间复杂度计算，感觉并不是最优解，至少不如我的快
     * <p>
     * 通过俩个int变量记录，然后使用双层for循环变量，寻找俩个相同字母
     * 最终将当前位置和起始位置求差，再于记录的maxLength求最大，返回即是结果
     *
     * @param s 入参字符串
     * @return 返回最长不重复子串长度
     */
    public static int lengthOfLongestSubstringNo1(String s) {
        char[] charArr = s.toCharArray();
        if (charArr.length == 0)
            return 0;
        int maxLength = 0;
        int baseIndex = 0;
        int i;
        for (i = baseIndex + 1; i < charArr.length; i++) {
            for (int j = baseIndex; j < i; j++) {
                if (charArr[j] == charArr[i]) {
                    maxLength = (i - baseIndex) > maxLength ? (i - baseIndex) : maxLength;
                    baseIndex = j + 1;
                    break;
                }
            }
        }
        maxLength = (i - baseIndex) > maxLength ? (i - baseIndex) : maxLength;
        return maxLength;
    }
}
