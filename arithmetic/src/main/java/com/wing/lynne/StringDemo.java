package com.wing.lynne;

import com.google.common.base.Joiner;

import java.lang.reflect.Field;


/**
 * String#intern返回常量池中的该对象
 * 不同版本的字符串写入常量池的逻辑不一致
 * 参考：https://www.cnblogs.com/Kidezyq/p/8040338.html
 * jdk1.7为分界线，
 * 系统第一次出现的字符串的地址值，写入常量池，后续获取都使用该对象
 * <p>
 * 分析字符串的题目时要注意编译器优化，最普通的字面值链接，会在编译期间被直接优化
 */
public class StringDemo {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        String join = Joiner.on("_").skipNulls().join("a", null);
        System.out.println(join);

//        注释打开和不打开是俩种结果
//        jdk版本也会影响返回值（以1.7为分界线）

//        String z = "aaabbb";

        String c4 = "abc4";
        String c4c = "abc" + c4.length();

        String a = new StringBuffer("aaa").append("bbb").toString();
        String b = new StringBuffer("ja").append("va").toString();
        String c = new StringBuffer("ty").append("pe").toString();

        System.out.println("-----------------------");
        System.out.println(a.intern() == a);
        System.out.println(b.intern() == b);
        System.out.println(c.intern() == c);

        String value = "wing";

        Field field = String.class.getDeclaredField("value");
        field.setAccessible(true);
        field.set(value, "lynne".toCharArray());

        System.out.println("-----------------------");
        System.out.println(value);

        System.out.println("-----------------------");
        System.out.println("lynne" == value);
        System.out.println("wing" == value);


        System.out.println("-------- start --------");
        System.out.println("lynne".equals(value));
        System.out.println("wing".equals(value));
        System.out.println("-------- end ----------");

    }


}
