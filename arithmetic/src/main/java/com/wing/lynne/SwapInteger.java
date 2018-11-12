package com.wing.lynne;

import java.lang.reflect.Field;

/**
 * main方法定义俩个Integer对象，实现swap方法，交换这俩个Integer对象的值
 */
public class SwapInteger {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Integer a = 20;
        Integer b = 30;

        System.out.println("a = " + a + " ,b = " + b);

        swap(a, b);

        System.out.println("a = " + a + " ,b = " + b);

//        轻易不要打开尝试，会刷新三观认知的
//        Integer c = 20;
//        System.out.println("c = " + c);
    }

    private static void swap(Integer a, Integer b) throws NoSuchFieldException, IllegalAccessException {

        //todo
        Field field = Integer.class.getDeclaredField("value");
        field.setAccessible(true);

        int temp = a.intValue();

        //此处由于jdk5.0的自动装箱导致temp被当作对象类型做了处理
        //所以使用Field#set方法会出现一些问题
        //当赋值的数据处于[-128,127]时，temp从IntegerCache中读取数据
        //第二次赋值的时候，读到了cache中的，被第一次set覆盖修改后的cache值
        //出现交换后，a发生交换  b读取到了修改后的IntegerCache对象   看起来b没有发生变化，实际上做了b=a的操作
        field.set(a, b);//IntegerCache[a]=IntegerCache[b]
        field.set(b, temp);//IntegerCache[b]=IntegerCache[temp]  此处temp=a ---->  IntegerCache[b]=IntegerCache[a]

        //正确的交换方法1
//        field.setInt(a,b);
//        field.setInt(b,temp);

        //正确的交换方法2
        //由于自动拆装箱走的是Integer#ValueOf，会从IntegerCache中读取，而构造方法不会读取IntegerCache所以可以正常返回
//        field.set(a,new Integer(b));
//        field.set(b,new Integer(temp));

    }

}


/**
 * 总结
 * 1.Integer类final类
 * 2.Integer类中的value字段 private final，即对象不能在被赋值
 * 3.Integer类中IntegerCache类的cache[]数组中的值是可以通过反射改变的
 * 4.Field#setAccessible方法只是修改了一个override的判断标志位，在后续的set方法调用时，判断!override如果为false就不再进行权限检查
 * 5.避开IntegerCache的方法，一使用基本数据类型，避开拆装箱，二使用new Integer，这样不会走valueof方法，也不会有IntegerCache的影响
 */
