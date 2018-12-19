package com.wing.lynne.jdkApiDemo;

import org.springframework.util.StringUtils;

/**
 * spring core模块中对与Bean标签的name字段解析，会有这样的api使用，被解析的元素会座位bean的别名使用
 */
public class StringTokenizerTest {

    public static void main(String[] args) {

        String beanName = "wing,;lynne,;myson,;mydocuter";

        String[] strings = StringUtils.tokenizeToStringArray(beanName, ",;");

        for (String s : strings) {
            System.out.println(s);
        }


    }
}
