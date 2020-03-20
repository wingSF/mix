package com.wing.config;


import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
@EnableWebMvc
@ConditionalOnProperty(name = "swagger.open", havingValue = "true")
public class SwaggerConfig {


    @Bean
    public Docket createRestApi() {
        Set<String> producesList = new HashSet<>();
        producesList.add("application/json");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .produces(producesList)
                .select()
                //指定扫描路径
                .apis(RequestHandlerSelectors.basePackage("com.wing.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    //文档信息说明和个人信息配置
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("${项目标题}")
                .description("${项目描述}")
                .contact(new Contact("${developer.name}", "", "${developer.email}"))
                .version("1.0")
                .build();
    }
}
