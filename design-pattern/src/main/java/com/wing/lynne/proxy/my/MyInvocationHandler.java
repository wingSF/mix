package com.wing.lynne.proxy.my;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public interface MyInvocationHandler extends InvocationHandler {

    Object invoke(Object proxy, Method method, Object[] args) throws Exception;
}
