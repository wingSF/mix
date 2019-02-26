package com.wing.lynne;

public class TryCatchExceptionDemo {

    public static void main(String[] args){

        System.out.println(hello());

    }

    public static int hello() {

        int i = 0;

        try {

            i = 1;

            return i;

        } catch (Exception e) {

            i = 2;

            return i;

        } finally {

            i = 3;

            return i;

        }
    }


}
