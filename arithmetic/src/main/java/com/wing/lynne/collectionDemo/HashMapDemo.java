package com.wing.lynne.collectionDemo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * hashmap常见操作及原理分析
 */
public class HashMapDemo {

    public static void main(String[] args) {

        //尝试将对象作为key，然后修改对象属性
        coupleMath();

        Map map = new HashMap();

        map.put(map,"123");
        map.put("321","123");

        System.out.println(map);
    }

    /**
     * 使用map做一个key，value对象
     * 修改key对象的值
     * 1.调用remove方法
     * 2.remove后查看集合
     * 3.根据user对象从map中get
     *
     * 注意:
     *      不要使用易变的对象做为hashmap的key，会导致hashmap这样的数据结构，在按照key查找的时候无法定位到元素位置
     * 奇技淫巧:
     *      也不能很绝对的说不可以，毕竟hashmap的注释写的是great care must be exercised if mutable objects are used as map keys
     *      使用被注释掉的hashcode/equals方法，再次运行
     * 结论:
     *      由于hashmap判断元素相等会先调用对象的hashcode，确定在数组中的下标位置，然后根据equals方法找到相等对象，所以只要修改的field不影响这个判断就没有问题。
     *
     * 附加结论:
     *      重写对象的hashcode和equals方法时要求，如果hashcode不相同，equals一定不同，如果hashcode相同，equals可以不同
     */
    private static void coupleMath() {
        Map userMap = new HashMap();

        User user = new User();
        user.age = 18;
        user.name = "wing";

        //使用user对象做为key
        userMap.put(user, "lynne");

        user.age = 19;

        Object remove = userMap.remove(user);
        System.out.println("remove result " + remove);
        System.out.println("after remove map is " + userMap);
        System.out.println("find user in map is " + userMap.get(user));
    }


    static class User {

        private String name;
        private int age;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return age == user.age &&
                    Objects.equals(name, user.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }


//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            User user = (User) o;
//            return Objects.equals(name, user.name);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(name);
//        }
    }
}
