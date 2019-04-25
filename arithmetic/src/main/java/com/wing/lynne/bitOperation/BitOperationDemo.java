package com.wing.lynne.bitOperation;

public class BitOperationDemo {

    public static void main(String[] args) {

        System.out.println(1|1);//1|1 = 1
        System.out.println(0|0);//0|0 = 0

        System.out.println(0|1);//0|1 = 1
        System.out.println(1|0);//1|0 = 1

        System.out.println(1&1);//1&1 = 1
        System.out.println(0&0);//0&0 = 0

        System.out.println(1&0);//1&0 = 0
        System.out.println(0&1);//0&1 = 0

        System.out.println(~1);

    }
}
