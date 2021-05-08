package com.wing.lynne;

import org.joda.time.DateTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioReadFile {

    public static void main(String[] args) throws IOException {

        DateTime dateTime = new DateTime("2022-09-10");//预产期
        DateTime dateTime1 = dateTime.minusDays(280);//正常孕期
        System.out.println(dateTime1);//开始时间

        String fileName = NioReadFile.class.getClassLoader().getResource("file.txt").getPath();//获取文件路径

        FileInputStream fileInputStream = new FileInputStream(new File(fileName));

        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(100);
        channel.read(buffer);

        buffer.flip();

        while (buffer.remaining() > 0) {

            byte b = buffer.get();
            //此处可涉及编码解码
            System.out.println((char)b);


        }

        buffer.clear();

        channel.close();

    }

}
