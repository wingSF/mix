package com.wing.lynne.jdkApiDemo;


//todo 从当前包中移出去

import org.apache.commons.lang3.StringUtils;

/**
 * 使用apache common lang3 判断空字符串
 * 区分blank和empty
 */
public class ApacheCommonsLang3StringBlankEmptyDemo {

    public static void main(String[] args) {

        String s1 = "";
        String s2 = "  ";


        //true
        System.out.println(StringUtils.isBlank(s1));
        //true
        System.out.println(StringUtils.isEmpty(s1));


        //true
        System.out.println(StringUtils.isBlank(s2));
        //false
        System.out.println(StringUtils.isEmpty(s2));

    }
}
