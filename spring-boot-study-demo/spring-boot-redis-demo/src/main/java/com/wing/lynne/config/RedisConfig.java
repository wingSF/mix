package com.wing.lynne.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RLiveObjectService;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {

    private String host;
    private String port;

    @Bean
    RedissonClient redisson(){
        Config config = new Config();

        config.useSingleServer().setAddress("redis://"+host+":"+port);

        return Redisson.create(config);
    }

    @Bean
    RLiveObjectService rLiveObjectService(){
        RedissonClient redisson = redisson();
        return redisson.getLiveObjectService();
    }
}
