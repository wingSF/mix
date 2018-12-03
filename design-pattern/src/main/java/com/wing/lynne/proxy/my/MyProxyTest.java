package com.wing.lynne.proxy.my;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class MyProxyTest {

    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Target target = (Target) new MyProxy().getInstance(new Target());

        System.out.println(target.getClass());

        target.doBusiness();
    }
}
