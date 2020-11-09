package com.wing.lynne.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class CacheBuilderDemo {

    public static void main(String[] args) throws InterruptedException {

        Cache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(1,TimeUnit.SECONDS)
                .build();

        String result = cache.getIfPresent("wing");

        System.out.println(result);

        cache.put("wing","lynne");

        result = cache.getIfPresent("wing");

        System.out.println(result);

        TimeUnit.SECONDS.sleep(1);

        result = cache.getIfPresent("wing");

        System.out.println(result);

    }
}
