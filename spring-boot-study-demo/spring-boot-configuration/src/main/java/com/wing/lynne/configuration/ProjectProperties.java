package com.wing.lynne.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 项目基础配置信息
 */
@Data
@Component
@ConfigurationProperties(prefix = "project")
public class ProjectProperties {

    private Business1Properties business1Properties;
    private Business2Properties business2Properties;
    private Business3Properties business3Properties;

    @Data
    public static class Business1Properties {
        private String properties1;
        private String properties2;
        private String properties3;
    }

    @Data
    public static class Business2Properties {
        private Integer properties1;
    }

    @Data
    public static class Business3Properties {
        private String properties1;
    }

    @PostConstruct
    public void afterInit(){
        System.out.println(business1Properties);
        System.out.println(business2Properties);
        System.out.println(business3Properties);
    }
}
