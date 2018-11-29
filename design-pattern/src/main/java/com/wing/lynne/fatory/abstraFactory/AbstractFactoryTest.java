package com.wing.lynne.fatory.abstraFactory;

/**
 * 抽象工厂 定义的支持的范围
 * 实现类 决定了每个支持的实现
 * api的变更，只需要发布版本即可
 * api的调用方，只能选择方法，版本不一致，会依赖不到需要的项，不会出现代码级别的错误
 */
public class AbstractFactoryTest {

    public static void main(String[] args) {

        MilkFactory milkFactory = new MilkFactory();

        System.out.println(milkFactory.getMengniu().getName());

    }
}
