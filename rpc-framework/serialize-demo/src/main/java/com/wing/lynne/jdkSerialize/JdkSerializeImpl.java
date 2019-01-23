package com.wing.lynne.jdkSerialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JdkSerializeImpl<T> {

    public byte[] serialize(T obj) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(obj);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }


    public T deSerialize(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            return (T) objectInputStream.readObject();

        } catch (Exception e) {
            throw new RuntimeException("deSerialize error");
        }
    }
}
