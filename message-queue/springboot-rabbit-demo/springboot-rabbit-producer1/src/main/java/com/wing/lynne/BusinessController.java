package com.wing.lynne;

import com.wing.lynne.producer.RabbitProducer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BusinessController {

    @Resource
    RabbitProducer rabbitProducer;


    @RequestMapping(value = "sendMessage1")
    public void sendMessage1(@RequestParam String message){
        rabbitProducer.sendToQueue1(message);
    }

    @GetMapping(value = "sendMessage2")
    public void sendMessage2(@RequestParam String message){
        rabbitProducer.sendToQueue2(message);
    }

    @GetMapping(value = "sendMessage3")
    public void sendMessage3(@RequestParam String message){
        rabbitProducer.sendTopic1(message);
    }

    @GetMapping(value = "sendMessage4")
    public void sendMessage4(@RequestParam String message){
        rabbitProducer.sendTopic2(message);
    }



}
