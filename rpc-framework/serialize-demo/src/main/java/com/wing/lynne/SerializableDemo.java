package com.wing.lynne;

import com.google.protobuf.ByteString;
import com.wing.lynne.jdkSerialize.JdkSerializeImpl;
import com.wing.lynne.po.proto.User;
import com.wing.lynne.xmlSerialize.XmlSerializeImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SerializableDemo {

    public static void main(String[] args) throws IOException {

        jdkSerialize();

        xmlSerialize();

        protobufSerialize();

    }

    private static void xmlSerialize() throws IOException {

        com.wing.lynne.po.User user = new com.wing.lynne.po.User();

        user.setName("wing");
        user.setAge(18);

        XmlSerializeImpl<com.wing.lynne.po.User> xmlSerialize = new XmlSerializeImpl();

        byte[] bytes = xmlSerialize.serialize(user);

        com.wing.lynne.po.User deSerialize = xmlSerialize.deSerialize(bytes);

        System.out.println("xml : " + deSerialize);

        writeToFile(bytes, "UserXml.txt");

    }

    private static void protobufSerialize() throws IOException {

        User.UserProto user = User.UserProto.newBuilder().setName("wing").setAge(18).build();

        ByteString byteString = user.toByteString();

        User.UserProto userProto = User.UserProto.parseFrom(byteString);

        System.out.println("protobuf : " + userProto);

        writeToFile(byteString.toByteArray(), "UserProtobuf.txt");

    }

    private static void writeToFile(byte[] bytes, String filename) throws IOException {
        /**------------------------- 写入文件，确实小很多 -----------------------------------------------*/
        FileOutputStream fileOutputStream = new FileOutputStream(new File("/Users/wing/Desktop", filename));

        fileOutputStream.write(bytes);

        fileOutputStream.close();
    }

    private static void jdkSerialize() throws IOException {
        /**------------------ JDK 序列化 ---------------------------------------*/
        com.wing.lynne.po.User son1 = new com.wing.lynne.po.User();

        son1.setName("wing-son1");
        son1.setAge(1);

        com.wing.lynne.po.User user = new com.wing.lynne.po.User();

        user.setName("wing");
        user.setAge(13);
        user.getChildren().add(son1);


        JdkSerializeImpl<com.wing.lynne.po.User> jdkSerialize = new JdkSerializeImpl();

        byte[] serialize = jdkSerialize.serialize(user);

        com.wing.lynne.po.User deSerializeUser = jdkSerialize.deSerialize(serialize);

        System.out.println("jdk : " + deSerializeUser);

//        System.out.println(user == deSerializeUser);

//        System.out.println(user.getChildren().get(0) == son1);

        writeToFile(serialize, "UserJDk.txt");
    }
}
