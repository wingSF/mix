package com.wing.lynne.strategy.sort;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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


        /*------------------------------------------------------------------------------------------------------------*/
        /**
         *  之前的都是简单的排序，当涉及对象排序的时候，会出现一些异常情况，常见于比较条件不严谨，下面是异常信息
         * java.lang.IllegalArgumentException: Comparison method violates its general contract!
         *  at java.util.TimSort.mergeHi(TimSort.java:899)
         *  at java.util.TimSort.mergeAt(TimSort.java:516)
         *  at java.util.TimSort.mergeCollapse(TimSort.java:441)
         *  at java.util.TimSort.sort(TimSort.java:245)
         *  at java.util.Arrays.sort(Arrays.java:1512)
         *  at java.util.ArrayList.sort(ArrayList.java:1462)
         *  at java.util.Collections.sort(Collections.java:175)
         */


        List<Modal> modalList = new ArrayList<>();

        Modal modal1 = new Modal("sf", 2);
        Modal modal2 = new Modal("st", 3);
        Modal modal3 = new Modal("spe", 1);
        Modal modal4 = new Modal("wr", 6);
        Modal modal5 = new Modal("wds", 5);
        Modal modal6 = new Modal("wds", 5);

        modalList.add(modal1);
        modalList.add(modal2);
        modalList.add(modal3);
        modalList.add(modal4);
        modalList.add(modal5);
        modalList.add(modal6);

        Collections.sort(modalList, (Comparator<Modal>) (o1, o2) -> {

            if (!o1.getName().equals(o2.getName())) {
                if (o1.getName().contains("s")) {
                    return 1;
                }

                if (o2.getName().contains("s")) {
                    return -1;
                }
            }

            return 0;
        });

        System.out.println(modalList);

    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Modal {
        private String name;
        private int age;
    }
}
