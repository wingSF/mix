package com.wing.lynne.controller;


import com.google.common.collect.Lists;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/test")
public class TestController {

    @Resource
    RedisTemplate redisTemplate;

    @GetMapping("/parallelAppend")
    public void addUser() {



        ArrayList<String> stringArrayList = Lists.newArrayList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");


        stringArrayList.parallelStream().forEach(s -> {

            Integer appendResult = redisTemplate.boundValueOps("wing").append(s);

            if(appendResult> 15){
                String  tempResult = redisTemplate.opsForValue().getAndSet("wing", "").toString();

                if(tempResult.length()<15){
                    redisTemplate.opsForValue().append("wing",tempResult);
                }else{
                    System.out.println(tempResult);
                }
            }

        });

        String result = redisTemplate.opsForValue().get("wing").toString();

        System.out.println();
    }
}
