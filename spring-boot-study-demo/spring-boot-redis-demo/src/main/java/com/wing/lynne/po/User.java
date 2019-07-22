package com.wing.lynne.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

@Builder
@Data
@REntity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @RId
    private String redisKey;

    private String name;
    private int age;
    private String address;

    private User son;
}
