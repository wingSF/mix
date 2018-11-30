package com.wing.lynne.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PojoTest {

    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {

        Pojo pojo = new Pojo();

        pojo.setName("pojo");
        pojo.getList().add("123");

        Pojo clone = (Pojo) pojo.clone();

        System.out.println(clone == pojo);
        System.out.println(clone.getName() == pojo.getName());
        System.out.println(clone.getList().size() == pojo.getList().size());

        //此处是浅复制，对象类型的属性，直接复制了对象的地址值，这样的复制
        System.out.println(clone.getList() == pojo.getList());

/**  ------------------------一下部分是深克隆方式一：序列化-----------------------------------------------------------------------------------*/

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(pojo);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        Pojo deepClone = (Pojo) objectInputStream.readObject();

        System.out.println(deepClone == pojo);
        System.out.println(deepClone.getName() == pojo.getName());
        System.out.println(deepClone.getName().equals(pojo.getName()));
        System.out.println(deepClone.getList().size() == pojo.getList().size());

        //此处是浅复制，对象类型的属性，直接复制了对象的地址值，这样的复制
        System.out.println(deepClone.getList() == pojo.getList());


/**  ------------------------一下部分是深克隆方式二：递归实现clone，重写clone方法-----------------------------------------------------------------------------------*/

        PojoParent pojoParent = new PojoParent();

        Pojo pojo1 = new Pojo();

        pojoParent.setPojo(pojo);

        PojoParent clonePojoParent = (PojoParent) pojoParent.clone();

        System.out.println(pojoParent.getPojo()==clonePojoParent.getPojo());

    }
}
