package com.wing.lynne;

public class StringDemo {

    public static void main(String[] args) {

//        注释打开和不打开是俩种结果
//        jdk版本也会影响返回值

//        String c = "aaabbb";

        String a = new StringBuffer("aaa").append("bbb").toString();
        String b = new StringBuffer("ja").append("va").toString();

        System.out.println(a.intern() == a);
        System.out.println(b.intern() == b);

    }


}
