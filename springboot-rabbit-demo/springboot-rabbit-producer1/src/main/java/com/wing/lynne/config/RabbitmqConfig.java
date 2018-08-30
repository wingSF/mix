package com.wing.lynne.config;

import com.wing.lynne.constant.RabbitmqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqConfig {

    @Bean
    public Queue innerBusiness1Queue() {
        return new Queue(RabbitmqConstant.BUSINESS_1_QUEUE_NAME);
    }

    @Bean
    public Queue innerBusiness2Queue() {
        return new Queue(RabbitmqConstant.BUSINESS_2_QUEUE_NAME);
    }

    @Bean
    public Queue innerBusiness3Queue() {
        return new Queue(RabbitmqConstant.BUSINESS_3_QUEUE_NAME);
    }

    @Bean
    public TopicExchange business1Exchange() {
        return new TopicExchange(RabbitmqConstant.BUSINESS_1_TOPIC_NAME);
    }

    @Bean
    public TopicExchange business2Exchange() {
        return new TopicExchange(RabbitmqConstant.BUSINESS_2_TOPIC_NAME);
    }

    @Bean
    public Binding bindExchange() {
        return BindingBuilder.bind(innerBusiness3Queue()).to(business1Exchange()).with(RabbitmqConstant.ROUTER_KEY_1);
    }
}
