package com.wing.lynne;

import com.sun.deploy.util.StringUtils;

public class StringUtil {

    public static boolean isNotEmpty(final CharSequence cs) {
        System.out.println("哼");
        return !isEmpty(cs);
    }


    public static boolean isEmpty(final CharSequence cs) {
        System.out.println("哈");
        return cs == null || cs.length() == 0;
    }

    public static boolean startWith(String param,String prefix){
        return false;
    }
}
