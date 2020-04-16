package com.wing.lynne;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setThreadNamePrefix("demo-");
        taskScheduler.setPoolSize(5);
        return taskScheduler;
    }

    @Bean("simpleSync")
    public ThreadPoolTaskScheduler taskScheduler1() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setThreadNamePrefix("demo1-");
        taskScheduler.setPoolSize(5);
        return taskScheduler;
    }
}
