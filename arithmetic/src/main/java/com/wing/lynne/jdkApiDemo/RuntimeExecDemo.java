package com.wing.lynne.jdkApiDemo;

import java.io.IOException;
import java.io.OutputStream;

public class RuntimeExecDemo {

    public static void main(String[] args) throws IOException {

        Process process = Runtime.getRuntime().exec("java -version");


        OutputStream outputStream = process.getOutputStream();

        byte[] b = new byte[1024];

        outputStream.write(b);

        System.out.println(new String(b));


    }

}
