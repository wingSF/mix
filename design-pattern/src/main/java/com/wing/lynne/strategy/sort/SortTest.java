package com.wing.lynne.strategy.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortTest {

    public static void main(String[] args) {

        List list = Arrays.asList(1, 2, 3, 4, 3, 4, 2, 4, 2, 3, 4, 23, 4, 23, 2, 32, 423, 424, 3);

        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        System.out.println(list);

        list.sort((Comparator<Integer>) (o1, o2) -> o2 - o1);

        System.out.println(list);


    }
}
