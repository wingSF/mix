package com.wing.lynne;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 演示参数参数对象类型，且对象类型属于Immutable类型
 * 该类型的对象，每次value发生变更都会重新生成一个对象
 *
 * 在#incrByUnsafe方法中，使用unsafe类直接对对象的value值进行修改，这样可以证明一句话
 *  在java中，基本类型数据作为参数传递，传值，对象类型数据作为参数传递，传对象的引用|地址值
 *
 * 要特别注意Integer的缓冲池，一旦被修改缓冲池中的对象value成功，别人会很迷惑
 *  效果是只要用到装箱的地方，实际的值总会与预期的不一致
 *
 * unsafe类除外，unsafe可以直接修改被final标识的字段value值，但这个时候是不会生成新的对象
 *
 */
public class IntegerDemo {

    private static Unsafe unsafe = null;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Integer paramIndex = 200;
        Integer resultParamIndex = incr(paramIndex);
        System.out.println(paramIndex == resultParamIndex);
        System.out.println("paramIndex = " + paramIndex);

        Integer paramIndex1 = 300;
        Integer paramIndex1Result = incrByUnSafe(paramIndex1);
        System.out.println(paramIndex1 == paramIndex1Result);
        System.out.println("paramIndex1 = " + paramIndex1);

        Integer numberInIntegerCache = 10;
        Integer incrByUnSafe = incrByUnSafe(numberInIntegerCache);
        System.out.println(numberInIntegerCache==incrByUnSafe);
        System.out.println(incrByUnSafe);
        Integer numberTemp = 10;
        System.out.println(numberTemp);


    }

    private static Integer incrByUnSafe(Integer paramIndex) throws NoSuchFieldException, IllegalAccessException {

        getUnsafe();

        long valueOffset = unsafe.objectFieldOffset(Integer.class.getDeclaredField("value"));

        unsafe.putInt(paramIndex, valueOffset, paramIndex.intValue()+1);

        return paramIndex;

    }


    private static Integer incr(Integer paramIndex) {
        paramIndex++;
        return paramIndex;
    }

    private static void getUnsafe() throws NoSuchFieldException, IllegalAccessException {

        if (unsafe == null) {
            Class<Unsafe> unsafeClass = Unsafe.class;

            Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");

            unsafeField.setAccessible(true);

            unsafe = (Unsafe) unsafeField.get(unsafeClass);
        }

    }
}
