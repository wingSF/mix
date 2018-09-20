package com.wing.lynne;

import com.wing.lynne.config.RocketProperties;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.groups.Default;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
public class BusinessController {

    @Autowired
    DefaultMQProducer producer;

    @Autowired
    RocketProperties rocketProperties;


    @RequestMapping(value = "sendMessageSync")
    public void sendMessage1(@RequestParam String message) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {

        Message msg = new Message(rocketProperties.getTopic(), "sendSync", ("Hello RocketMQ " + message).getBytes(RemotingHelper.DEFAULT_CHARSET));

        SendResult sendResult = producer.send(msg);

        System.out.println(sendResult);

    }

    @GetMapping(value = "sendMessageAsync")
    public void sendMessage2(@RequestParam String message) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {


        Message msg = new Message(rocketProperties.getTopic(), "sendASync", ("Hello RocketMQ " + message).getBytes(RemotingHelper.DEFAULT_CHARSET));

        producer.send(msg, new SendCallback() {
            public void onSuccess(SendResult sendResult) {
                System.out.println("async send success " + sendResult.getMsgId());
            }

            public void onException(Throwable e) {
                System.out.println("async send exception " + e.getMessage());
                e.printStackTrace();
            }
        });

    }

    @GetMapping(value = "sendMessageOnewayMode")
    public void sendMessage3(@RequestParam String message) throws MQClientException, RemotingException, InterruptedException, UnsupportedEncodingException {

        Message msg = new Message(rocketProperties.getTopic(), "sendOneWay", ("Hello RocketMQ " + message).getBytes(RemotingHelper.DEFAULT_CHARSET));

        producer.sendOneway(msg);

    }


}
