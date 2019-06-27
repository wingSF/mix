package com.wing.lynne;

/**
 * 演示参数参数对象类型，且对象类型属于Immutable类型
 * 该类型的对象，每次value发生变更都会重新生成一个对象
 */
public class IntegerDemo {

    public static void main(String[] args) {
        String str = "wing";
        System.out.println("main before addStr address = " + str.hashCode());
        String resultStr = addStr(str);
        System.out.println("main after addStr address = " + str);
        System.out.println(str == resultStr);

        Integer paramIndex = 200;
        System.out.println("main before incr address = " + paramIndex.hashCode());
        Integer resultParamIndex = incr(paramIndex);
        System.out.println("main after incr address = " + paramIndex);
        System.out.println(paramIndex == resultParamIndex);
    }

    private static String addStr(String str) {
        System.out.println("before addStr address = " + str.hashCode());
        str += "lynne";
        System.out.println("after addStr address = " + str.hashCode());
        System.out.println("addStr:" + str);
        return str;
    }

    private static Integer incr(Integer paramIndex) {
        System.out.println("before incr address = " + paramIndex.hashCode());
        paramIndex++;
        System.out.println("incr:" + paramIndex);
        System.out.println("after incr address = " + paramIndex.hashCode());
        return paramIndex;
    }

}
