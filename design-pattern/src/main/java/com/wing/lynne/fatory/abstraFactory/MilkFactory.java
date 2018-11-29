package com.wing.lynne.fatory.abstraFactory;

import com.wing.lynne.fatory.Mengniu;
import com.wing.lynne.fatory.Milk;
import com.wing.lynne.fatory.Telunsu;
import com.wing.lynne.fatory.Yili;

public class MilkFactory extends AbstractFactory {


    public Milk getMengniu() {
        return new Mengniu();
    }

    public Milk getYili() {
        return new Yili();
    }

    public Milk getTelunsu() {
        return new Telunsu();
    }
}
