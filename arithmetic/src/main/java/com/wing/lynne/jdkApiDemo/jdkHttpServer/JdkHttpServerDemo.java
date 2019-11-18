package com.wing.lynne.jdkApiDemo.jdkHttpServer;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class JdkHttpServerDemo {

    public static void main(String[] args) throws IOException {

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8888), 0);

        httpServer.createContext("/timelyEventInfo", httpExchange -> {

            TempObject object1 = new TempObject();
            object1.setEventId("1");
            object1.setAppToken("apptoken1");
            object1.setEventName("eventname1");
            object1.setKafkaTopic("kafkaTopic1");

            String result = "[" + object1.toString() + "]";

            httpExchange.sendResponseHeaders(200, result.getBytes().length);
            httpExchange.getResponseBody().write(result.getBytes());
            httpExchange.close();
        });

        httpServer.start();

    }

    static class TempObject {
        /**
         * 事件id
         */
        public String eventId;
        /**
         * 事件名称
         */
        public String eventName;
        /**
         * 事件的所属应用的唯一标识
         */
        public String appToken;
        /**
         * 事件通过哪个topic，在druid中建立实时表
         * 需提前创建
         */
        public String kafkaTopic;

        public String getEventId() {
            return eventId;
        }

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getAppToken() {
            return appToken;
        }

        public void setAppToken(String appToken) {
            this.appToken = appToken;
        }

        public String getKafkaTopic() {
            return kafkaTopic;
        }

        public void setKafkaTopic(String kafkaTopic) {
            this.kafkaTopic = kafkaTopic;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"eventId\":\"" + eventId + "\"," +
                    "\"eventName\":\"" + eventName + "\"," +
                    "\"appToken\":\"" + appToken + "\"," +
                    "\"kafkaTopic\":\"" + kafkaTopic + "\"" +
                    '}';
        }
    }
}
