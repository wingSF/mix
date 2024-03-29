package com.wing.lynne;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPI {

    public static void main(String[] args) {

        Person person1 = new Person("a", 1);
        Person person2 = new Person("b", 2);
        Person person3 = new Person("a", 2);

        ArrayList<Person> personArrayList = new ArrayList<>();
        personArrayList.add(person1);
        personArrayList.add(person2);
        personArrayList.add(person3);

        //排序、排序反转
        personArrayList.sort(Comparator.comparing(Person::getAge));
        personArrayList.sort(Comparator.comparing(Person::getAge).reversed());

        Map<String, List<Person>> personData = personArrayList.stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.toList()));

        List<Person> flatMapPersonList = personData.values()
                .stream()
                .flatMap(personList -> personList.stream())
                .collect(Collectors.toList());

        System.out.println(flatMapPersonList);

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);

        List<Integer> result = new ArrayList<>();

        integers.parallelStream().forEach(integer -> {
            List<Integer> resultList = new ArrayList<>();
            resultList.add(integer);
            resultList.add(integer * 2);
            result.addAll(resultList);
        });

        System.out.println(result);

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

//        演示异常抛出，打开注释，后续代码无法执行
//        Optional.ofNullable(null).orElseThrow(RuntimeException::new);
//        Optional.ofNullable(null).orElseThrow(()->new RuntimeException());

        //flatmap
        List<String> stringList = new ArrayList<>();

        stringList.add("1,2,3");
        stringList.add("4,5,6");

        stringList
                .stream()
                .map(value -> value.split(","))
                .flatMap(v -> Arrays.stream(v))
                .forEach(System.out::println);

        //获取流中的min值
        Optional<Integer> min = numberList
                .stream()
                .min(Integer::compareTo);
        if (min.isPresent()) {
            Integer integer = min.get();
            System.out.println("min number is " + integer);
        }

        Optional<Person> personWithMinAge = personList
                .stream()
                .min(Comparator.comparingInt(Person::getAge));
        System.out.println(personWithMinAge.get());


        Optional<Person> personWithMinName = personList
                .stream()
                .min(Comparator.comparing(Person::getName));
        System.out.println(personWithMinName.get());

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


        personList.parallelStream().collect(Collectors.toMap(Person::getName, Person::getAge));


    }

    public static void fun(int[] array) {
        array[0] = 0;
    }


    public static void mapMethod() {

    }

    public static class Person {

        String name;
        int age;

        public Person() {
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

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
