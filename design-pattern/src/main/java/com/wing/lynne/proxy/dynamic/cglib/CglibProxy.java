package com.wing.lynne.proxy.dynamic.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    public Object getInstance(Target target){

        Class<? extends Target> targetClass = target.getClass();

        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(targetClass);

        enhancer.setCallback(this);

        return enhancer.create();

    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println("proxy before business do something");
        methodProxy.invokeSuper(o,objects);
        System.out.println("proxy after business do something");

        return null;
    }
}
