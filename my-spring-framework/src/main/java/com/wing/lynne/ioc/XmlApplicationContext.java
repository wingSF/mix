package com.wing.lynne.ioc;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

public class XmlApplicationContext {

    public static void main(String[] args) {

        //制定资源位置
        ClassPathResource classPathResource = new ClassPathResource("application-context.xml");
        //默认工厂
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        //xmlBean解析器
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        //注册bean
        xmlBeanDefinitionReader.loadBeanDefinitions(classPathResource);

    }
}
