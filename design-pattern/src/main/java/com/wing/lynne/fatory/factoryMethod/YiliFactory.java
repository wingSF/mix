package com.wing.lynne.fatory.factoryMethod;

import com.wing.lynne.fatory.Milk;
import com.wing.lynne.fatory.Yili;

public class YiliFactory implements MilkFactory {

    public Milk getMilk() {
        return new Yili();
    }
}
