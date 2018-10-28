package com.wing.lynne.consumer;

import com.wing.lynne.constant.RabbitmqConstant;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer {

    @RabbitListener(queues = RabbitmqConstant.CONSUMER_1_QUEUE_NAME)
    public void process1(String message) {
        System.out.println(message);
    }

    @RabbitListener(queues = RabbitmqConstant.CONSUMER_2_QUEUE_NAME)
    public void process2(String message) {
        System.out.println(message);
    }

}