package com.wing.lynne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.LinkedHashMap;
import java.util.Map;


@SpringBootApplication
public class ConsumerApplication extends SpringBootServletInitializer {

    //第一种启动方式
//    public static void main(String[] args) {
//        SpringApplication.run(ConsumerApplication.class);
//    }

    //第二种启动方式
//    public static void main(String[] args){
//        SpringApplication springApplication = new SpringApplication(ConsumerApplication.class);
//
//        Map<String,Object> properties = new LinkedHashMap<>();
//
//        properties.put("server.port",0);
//
//        springApplication.setDefaultProperties(properties);
//
//        springApplication.run();
//    }

    //第三种启动方式
    public static void main(String[] args){
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ConsumerApplication.class);

        builder.properties("server.port=0");
        builder.run();
    }
}
