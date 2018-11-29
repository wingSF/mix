package com.wing.lynne.fatory.simpleFactory;

import com.wing.lynne.fatory.Mengniu;
import com.wing.lynne.fatory.Milk;
import com.wing.lynne.fatory.Telunsu;
import com.wing.lynne.fatory.Yili;

public class SimpleFactory {

    public Milk getMilk(String name) {

        if ("蒙牛".equals(name)) {
            return new Mengniu();
        } else if ("伊利".equals(name)) {
            return new Yili();
        } else if ("特仑苏".equals(name)) {
            return new Telunsu();
        } else {
            System.out.println("不能生产" + name);
            return null;
        }

    }

}
