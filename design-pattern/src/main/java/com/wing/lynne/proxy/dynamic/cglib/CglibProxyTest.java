package com.wing.lynne.proxy.dynamic.cglib;

import com.wing.lynne.proxy.dynamic.jdk.TargetInterface;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

public class CglibProxyTest {

    public static void main(String[] args) throws IOException {

        Target target = (Target) new CglibProxy().getInstance(new Target());

        System.out.println(target.getClass());

        target.doBusiness();


        byte[] targetInterfaceBytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{TargetInterface.class});

        FileOutputStream fileOutputStream = new FileOutputStream("/Users/wing/Desktop/targetInterfaceBytes.class");

        fileOutputStream.write(targetInterfaceBytes);

        fileOutputStream.close();

    }
}
