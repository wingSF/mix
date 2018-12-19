package com.wing.lynne;

import com.wing.lynne.producer.RabbitProducer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BusinessController {

    @Resource
    RabbitProducer rabbitProducer;

    @RequestMapping(value = "sendMessage")
    public String sendMessage(@RequestParam String message) {
        Map result = new HashMap<>();
        result.put("message", message);
        return "index";
    }

    @RequestMapping(value = "sendMessage1")
    public void sendMessage1(@RequestParam String message) {
        rabbitProducer.sendToQueue1(message);
    }

    @GetMapping(value = "sendMessage2")
    public void sendMessage2(@RequestParam String message) {
        rabbitProducer.sendToQueue2(message);
    }

    @GetMapping(value = "sendMessage3")
    public void sendMessage3(@RequestParam String message) {
        rabbitProducer.sendTopic1(message);
    }

    @GetMapping(value = "sendMessage4")
    public void sendMessage4(@RequestParam String message) {
        rabbitProducer.sendTopic2(message);
    }


}
