package com.wing.lynne.example1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassMetaData {

    //Author:wing
    //Date: 16/10/2018
    //current version:0
    //last modified: 16/10/2018
    //by: wing
    //reviewers: wing

    //class code

    @SafeVarargs
    static void m(List<String>... stringLists){

        Object[] array = stringLists;

        List<Integer> tmpList = Arrays.asList(42);

        array[0] = tmpList;

        String s = stringLists[0].get(0);

    }

    public static void main(String[] args) {
        m(new ArrayList<String>());
    }
}
