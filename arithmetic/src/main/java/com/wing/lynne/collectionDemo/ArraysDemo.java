package com.wing.lynne.collectionDemo;

import lombok.Data;

import java.util.Arrays;

public class ArraysDemo {

    public static void main(String[] args) {

        int[] array = {1, 2, 3, 4};
        int size = Arrays.asList(array).size();
        System.out.println(size);


        Integer[] objectArray = {1, 2, 3, 4};
        int objectSize = Arrays.asList(objectArray).size();
        System.out.println(objectSize);


        int[] arrayNew = new int[10];
        System.arraycopy(array, 0, arrayNew, 2, array.length);

        Address address = new Address();
        address.setAddress("address1");
        User user = new User();
        user.setAddress(address);
        user.setAge(15);

        Address address2 = new Address();
        address2.setAddress("address2");
        User user2 = new User();
        user2.setAddress(address2);
        user2.setAge(25);

        User[] userArray = new User[2];
        userArray[0] = user;
        userArray[1] = user2;

        User[] newUserArray = new User[3];

        //这里是deep clone
        System.arraycopy(userArray, 0, newUserArray, 0, userArray.length);

        System.out.println(newUserArray);
    }


    @Data
    static class User implements Cloneable{

        private Integer age;
        private Address address;
        private Object object = new Object();


    }

    @Data
    static class Address {
        private String address;
    }
}
