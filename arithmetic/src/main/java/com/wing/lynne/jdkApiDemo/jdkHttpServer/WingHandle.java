package com.wing.lynne.jdkApiDemo.jdkHttpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class WingHandle implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        httpExchange.sendResponseHeaders(200, 0);
        httpExchange.getResponseBody().write("i am lynne".getBytes());
        httpExchange.close();
    }
}
