package com.wing.lynne.provider;

import com.wing.lynne.inter.DemoService;

public class DemoServiceImpl implements DemoService {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
