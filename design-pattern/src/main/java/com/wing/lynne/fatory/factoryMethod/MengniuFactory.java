package com.wing.lynne.fatory.factoryMethod;

import com.wing.lynne.fatory.Mengniu;
import com.wing.lynne.fatory.Milk;

public class MengniuFactory implements MilkFactory {

    public Milk getMilk() {
        return new Mengniu();
    }
}
