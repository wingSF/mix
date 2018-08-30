package com.wing.lynne.producer;


import com.wing.lynne.constant.RabbitmqConstant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendToQueue1(String message) {
        rabbitTemplate.convertAndSend(RabbitmqConstant.BUSINESS_1_QUEUE_NAME, "发给business_queue_1，message是" + message);
    }

    public void sendToQueue2(String message) {
        rabbitTemplate.convertAndSend(RabbitmqConstant.BUSINESS_2_QUEUE_NAME, "发给business_queue_2，message是" + message);
    }

    public void sendTopic1(String message) {
        rabbitTemplate.convertAndSend(RabbitmqConstant.BUSINESS_1_TOPIC_NAME, RabbitmqConstant.ROUTER_KEY_1, "发给business_topic_1，message是" + message);
    }

    public void sendTopic2(String message) {
        rabbitTemplate.convertAndSend(RabbitmqConstant.BUSINESS_2_TOPIC_NAME, RabbitmqConstant.ROUTER_KEY_2, "发给business_topic_2，message是" + message);
    }

}
