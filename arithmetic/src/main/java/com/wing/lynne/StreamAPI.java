package com.wing.lynne;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPI {

    public static void main(String[] args) {

        int[] array = {1, 2, 3};
        fun(array);
        System.out.println(array);

        List<Integer> numberList = new ArrayList<>();
        List<Person> personList = new ArrayList<>();

        Person p1 = new Person();
        p1.setAge(1);
        p1.setName("a");

        Person p2 = new Person();
        p2.setAge(2);
        p2.setName("b");

        Person p3 = new Person();
        p3.setAge(2);
        p3.setName("c");

        Person p4 = new Person();
        p4.setAge(4);
        p4.setName("c");

        personList.add(p1);
        personList.add(p2);
        personList.add(p3);
        personList.add(p4);

        numberList.add(1);
        numberList.add(2);
        numberList.add(3);
        numberList.add(4);
        numberList.add(5);
        numberList.add(6);

        //获取流中的min值
        //todo    对象的如何获取
        Optional<Integer> min = numberList.stream().min(Integer::compareTo);
        if (min.isPresent()) {
            Integer integer = min.get();
            System.out.println("min number is " + integer);
        }

        personList.stream().min(Comparator.comparing(Person::getName));

        Map<Integer, String> collect1 = personList.stream().collect(Collectors.toMap(Person::getAge, Person::getName, (t1, t2) -> t1 + "," + t2));
        Map<Integer, String> collect2 = personList.stream().collect(Collectors.toMap(Person::getAge, Person::getName, (t1, t2) -> t2));

        System.out.println(collect1);
        System.out.println(collect2);

        //获取流中的max值
        Optional<Integer> max = numberList.stream().max(Integer::compareTo);
        if (max.isPresent()) {
            Integer integer = max.get();
            System.out.println("max number is " + integer);
        }

        //stream sort asc
        Stream<Integer> sorted = numberList.stream().sorted();
        sorted.forEach(integer -> System.out.print(integer + "<"));

        //stream sort desc
        Stream<Integer> sorted1 = numberList.stream().sorted(Comparator.comparingInt(o -> o));
        System.out.println();
        sorted1.forEach(integer -> System.out.print(integer + "<"));

        Stream<Integer> sorted2 = numberList.stream().sorted((o1, o2) -> o2 - o1);
        System.out.println();
        sorted2.forEach(integer -> System.out.print(integer + "<"));

        //stream filter
        //bigger than 3
        Stream<Integer> integerStream = numberList.stream().filter(integer -> integer > 3);
        System.out.println();
        integerStream.forEach(integer -> System.out.print(integer + ">3;  "));

        Stream<Person> personStream = personList.stream().filter(person -> person.getAge() > 1);
        System.out.println();
        personStream.forEach(person -> System.out.print(person + "-----"));

        //bigger than 1  smaller than 5
        Stream<Integer> integerStream1 = numberList.stream().filter(integer -> integer > 1)
                .filter(integer -> integer < 5);
        System.out.println();
        integerStream1.forEach(integer -> System.out.print("1<" + integer + "<5;  "));

        //original list
        System.out.println();
        numberList.forEach(integer -> System.out.print(integer + "-"));

    }

    public static void fun(int[] array) {
        array[0] = 0;
    }


    public static void mapMethod() {

    }

    public static class Person {

        String name;
        int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
