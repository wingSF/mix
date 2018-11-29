package com.wing.lynne.fatory.simpleFactory;

import com.wing.lynne.fatory.Milk;

import java.util.Objects;

/**
 * 简单工厂模式中
 * SimpleFactory相当于api端
 *     每次新增一个工厂的时候，无辜的代码会被影响
 *     如果每个人都自己new对象的话，不太统一
 * Test类的main方法相当于api的调用方，需要在获取的时候，传入参数
 */
public class SimpleFactoryTest {

    public static void main(String[] args) {

        SimpleFactory factory = new SimpleFactory();

        Milk milk = factory.getMilk("伊利");

        if(Objects.nonNull(milk)){
            System.out.println(milk.getName());
        }

    }
}
