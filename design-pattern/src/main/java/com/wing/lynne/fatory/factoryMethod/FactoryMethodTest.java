package com.wing.lynne.fatory.factoryMethod;

import com.wing.lynne.fatory.Milk;

/**
 * 工厂方法中有一个统计的接口指定了每个子工厂都需要实现的方法
 * 在子工厂中统一处理了bean的初始化过程
 * 每次新增产品不会对别的部分产生影响
 * 每次产品变更，只改自己的部门
 *
 * 调用方还是需要选择
 */
public class FactoryMethodTest {

    public static void main(String[] args) {

        MilkFactory milkFactory = new MengniuFactory();

        Milk milk = milkFactory.getMilk();

        System.out.println(milk.getName());

    }
}
