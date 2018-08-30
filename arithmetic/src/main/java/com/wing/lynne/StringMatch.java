package com.wing.lynne;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * 查找字符串数组中，与pattern字符串格式一致的字符串
 * ex：  abb，bcc，cdd 是可以匹配  xyy的
 * ex：  abc   是不可以匹配   xyy的
 * https://leetcode-cn.com/submissions/detail/5863592/
 */
public class StringMatch {

    public static void main(String[] args) {

        String[] words = {"abc", "deq", "mee", "aqq", "dkd", "ccc"};
        String pattern = "abb";
        List<String> resultStringList = findAndReplacePattern(words, pattern);

        System.out.println(resultStringList);
    }

    public static List<String> findAndReplacePattern(String[] words, String pattern) {

        List<String> resultStringList = new ArrayList<>();

        for (String word : words) {

            if (word.length() != pattern.length()) {
                continue;
            }

            ArrayList wordDistinctCharList = new ArrayList(getDistinctCharFromString(word));
            ArrayList patternDistinctCharList = new ArrayList(getDistinctCharFromString(pattern));

            if (wordDistinctCharList.size() != patternDistinctCharList.size()) {
                continue;
            }

            int flag = 0;

            b:
            for (int i = 0; i < wordDistinctCharList.size(); i++) {

                char wordDisChar = (Character) wordDistinctCharList.get(i);
                char patternDisChar = (Character) patternDistinctCharList.get(i);

                int fromIndex = 0;

                while (true) {

                    int wordDisCharIndex = word.indexOf(wordDisChar, fromIndex + 1);
                    int patternDisCharIndex = pattern.indexOf(patternDisChar, fromIndex + 1);

                    if (wordDisCharIndex == patternDisCharIndex) {

                        if (wordDisCharIndex == -1) {
                            break;
                        }

                        fromIndex = wordDisCharIndex;
                    } else {
                        flag = 1;
                        break b;
                    }

                }

            }

            if (flag == 0) {
                resultStringList.add(word);
            }


        }

        return resultStringList;
    }

    public static Set<Character> getDistinctCharFromString(String string) {

        LinkedHashSet set = new LinkedHashSet();

        char[] chars = string.toCharArray();

        for (char c : chars) {
            set.add(c);
        }

        return set;
    }
}
