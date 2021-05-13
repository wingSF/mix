package com.wing.lynne;

import lombok.Data;

@Data
public class Rectangle {

    private double width;
    private double height;

    public double getArea() {
        return width * height;
    }

    public static String getName(){
        System.out.println("o ho");
        return Rectangle.class.getName();
    }
}
