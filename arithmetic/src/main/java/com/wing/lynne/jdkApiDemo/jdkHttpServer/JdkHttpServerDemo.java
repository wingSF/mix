package com.wing.lynne.jdkApiDemo.jdkHttpServer;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class JdkHttpServerDemo {

    public static void main(String[] args) throws IOException {

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8888), 0);

        httpServer.createContext("/wing", new WingHandle());

        httpServer.start();

    }
}
