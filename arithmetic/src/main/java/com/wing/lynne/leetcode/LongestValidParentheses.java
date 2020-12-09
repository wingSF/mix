package com.wing.lynne.leetcode;

/**
 * //todo 知道使用栈可以解决该问题，但是多层嵌套的时候，还是没有很好的解决
 */
public class LongestValidParentheses {

    public static void main(String[] args) {
//        String data = "(()";
        String data = ")()())";
//        String data = "()(())";
//        String data = "(()())";
//        String data = "()(()";
        LongestValidParentheses longestValidParentheses = new LongestValidParentheses();
        System.out.println(longestValidParentheses.solution(data));

    }

    public int solution(String s) {

        //边界判断
        if (s == null || s.length() == 0 | s.indexOf("(") < 0) {
            return 0;
        }

        //获取有效数据
        int startPosition = s.indexOf("(");
        char[] dataCharArray = s.substring(startPosition).toCharArray();
        //记录返回值
        int count = 0;

        String[] tempArray = new String[dataCharArray.length];
        int tempPosition = 0;

        for (int i = 0; i < dataCharArray.length; i++) {

            if (i == 0 || tempPosition == 0) {
                tempArray[tempPosition] = "" + dataCharArray[i];
                tempPosition++;
            } else {

                if ("()".equals("" + tempArray[tempPosition - 1] + dataCharArray[i])) {
                    count += 2;
                    tempPosition--;
                    tempArray[tempPosition] = "" + count;
                    tempPosition++;

                    while (tempPosition > 1) {

                        if ("(".equals(tempArray[tempPosition - 1]) || ")".equals(tempArray[tempPosition - 1])) {
                            tempArray[tempPosition] = "" + count;
                            tempPosition++;
                            break;
                        } else {
                            count += Long.valueOf(tempArray[tempPosition - 1]);
                            tempPosition--;
                        }

                    }

                } else {
                    tempArray[tempPosition] = "" + dataCharArray[i];
                    tempPosition++;
                }

            }

        }

        return 0;

    }
}
