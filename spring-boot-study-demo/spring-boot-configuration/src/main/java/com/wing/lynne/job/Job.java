package com.wing.lynne.job;

import com.wing.lynne.configuration.ProjectProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@PropertySource("classpath:application.properties")
public class Job {

    @Resource
    ProjectProperties projectProperties;

    /**
     * 此处是无法使用projectProperties中的配置的
     * 可以借助propertysource，加载指定配置文件，然后@Value的方式，替换配置值
     */
    @Scheduled(fixedRateString = "${task.interval}")
//    @Scheduled(fixedRate = 1L)
    public void job1() {
        System.out.println("i am running " + new Date());
    }


}
