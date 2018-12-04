package com.wing.lynne.proxy.dynamic.jdk;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

public class JdkProxyTest {

    public static void main(String[] args) throws IOException {

        TargetInterface targetInterface = new JdkProxy<TargetInterface>().getInstance(new Target());

        System.out.println(targetInterface.getClass());

        targetInterface.doBusiness();

        byte[] targetInterfaceBytes = ProxyGenerator.generateProxyClass("ProxyByJdk", new Class[]{TargetInterface.class});

        FileOutputStream fileOutputStream = new FileOutputStream("/Users/wing/Desktop/ProxyByJdk.class");

        fileOutputStream.write(targetInterfaceBytes);

        fileOutputStream.close();

    }
}
