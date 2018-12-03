package com.wing.lynne.proxy.dynamic.cglib;

public class CglibProxyTest {

    public static void main(String[] args) {

        Target target = (Target) new CglibProxy().getInstance(new Target());

        System.out.println(target.getClass());

        target.doBusiness();

    }
}
