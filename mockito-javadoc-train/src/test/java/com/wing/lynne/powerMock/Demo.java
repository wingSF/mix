package com.wing.lynne.powerMock;

import lombok.AllArgsConstructor;
import org.assertj.core.util.Sets;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Demo {

    public static void main(String[] args) {

        List<Data> allDataList = new ArrayList<>();

        allDataList.add(new Data(1, 1, "123"));
        allDataList.add(new Data(2, 2, "123"));
        allDataList.add(new Data(3, 3, "123"));
        allDataList.add(new Data(4, 4, "123"));

        allDataList.add(new Data(5, 4, "321"));
        allDataList.add(new Data(6, 3, "321"));
        allDataList.add(new Data(7, 2, "321"));
        allDataList.add(new Data(8, 1, "321"));

        Map<String, TreeSet<Data>> aidTreeSet = allDataList.stream()
                .collect(Collectors.groupingBy(Data::getAid,
                        Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(Data::getType)))));

        System.out.println(aidTreeSet);

    }
}


@lombok.Data
@AllArgsConstructor
class Data{
    private long id;
    private int type;
    private String aid;
}
