package com.wing.lynne.po;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private long id;
    private String name;
    private int sex;
    private int age;
    private Date birthday;
    private String phoneNumber;
    private Province province;

}
