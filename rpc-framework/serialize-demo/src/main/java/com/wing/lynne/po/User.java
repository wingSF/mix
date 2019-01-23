package com.wing.lynne.po;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class User implements Serializable {


    private String name;
    private int age;

    private static String address = "china";
    private List<User> children = new ArrayList<User>();
}
