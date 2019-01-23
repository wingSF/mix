package com.wing.lynne.xmlSerialize;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlSerializeImpl<T> {

    XStream xStream = new XStream(new DomDriver());

    public byte[] serialize(T obj) {
        return xStream.toXML(obj).getBytes();
    }


    public T deSerialize(byte[] bytes) {
        return (T) xStream.fromXML(new String(bytes));
    }
}
