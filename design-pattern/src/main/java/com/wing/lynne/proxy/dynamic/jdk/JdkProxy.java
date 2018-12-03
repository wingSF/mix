package com.wing.lynne.proxy.dynamic.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxy<T> implements InvocationHandler {

    private T targetInterface;

    public T getInstance(T targetInterface){

        this.targetInterface = targetInterface;

        Class<?> targetInterfaceClass = targetInterface.getClass();

        ClassLoader classLoader = targetInterfaceClass.getClassLoader();
        Class<?>[] interfaces = targetInterfaceClass.getInterfaces();

        return (T) Proxy.newProxyInstance(classLoader,interfaces,this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("proxy before business do something");
        method.invoke(targetInterface,args);
        System.out.println("proxy after business do something");

        return null;
    }
}
