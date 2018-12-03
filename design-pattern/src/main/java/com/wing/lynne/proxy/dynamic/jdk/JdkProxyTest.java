package com.wing.lynne.proxy.dynamic.jdk;

public class JdkProxyTest {

    public static void main(String[] args) {

        TargetInterface targetInterface = new JdkProxy<TargetInterface>().getInstance(new Target());

        System.out.println(targetInterface.getClass());

        targetInterface.doBusiness();

    }
}
