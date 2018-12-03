package com.wing.lynne.proxy.my;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyProxy implements MyInvocationHandler {

    private Target target;

    public Object getInstance(Target target) throws IOException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

        this.target = target;

        Class<? extends Target> targetClass = target.getClass();

        Class<?>[] interfaces = targetClass.getInterfaces();

        return MyProxy.newInstance(new MyClassLoader(), interfaces, this);

    }

    private static Object newInstance(MyClassLoader loader, Class<?>[] interfaces, MyInvocationHandler h) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        //生成javasource
        String source = genernateSource(interfaces);

        //写入java文件
        String path = MyProxy.class.getResource("").getPath();
        System.out.println(path);
        File javaFile = new File(path,"Proxy007.java");
        FileWriter fileWriter = new FileWriter(javaFile);
        fileWriter.write(source);
        fileWriter.flush();
        fileWriter.close();

        //编译java文件
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manage = compiler.getStandardFileManager(null,null,null);
        Iterable iterable = manage.getJavaFileObjects(javaFile);

        JavaCompiler.CompilationTask task = compiler.getTask(null,manage,null,null,null,iterable);
        task.call();
        manage.close();

        //loadjava文件
        Class proxyClass = loader.findClass("Proxy007");
        Constructor c = proxyClass.getConstructor(MyInvocationHandler.class);
//        javaFile.delete();

        //创建实例
        return c.newInstance(h);

    }

    private static String genernateSource(Class<?>[] interfaces) {

        StringBuilder stringBuilder = new StringBuilder();

        String lineSeparator = System.getProperty("line.separator");

        stringBuilder.append("package com.wing.lynne.proxy.my;" + lineSeparator);
        stringBuilder.append("import com.wing.lynne.proxy.dynamic.jdk.TargetInterface;" + lineSeparator);
        stringBuilder.append("import java.lang.reflect.Method;" + lineSeparator);
        stringBuilder.append(lineSeparator);
        stringBuilder.append("public final class Proxy007 extends MyProxy implements " + interfaces[0].getName() + " {" + lineSeparator);
        stringBuilder.append(" private MyInvocationHandler myInvocationHandler;" + lineSeparator);
        stringBuilder.append("private static Method m3;" + lineSeparator);
        stringBuilder.append(" public Proxy007(MyInvocationHandler myInvocationHandler) {" + lineSeparator);
        stringBuilder.append("this.myInvocationHandler = myInvocationHandler;" + lineSeparator);
        stringBuilder.append("}" + lineSeparator);

        for (Method method : interfaces[0].getMethods()) {
            stringBuilder.append("public final " + method.getReturnType() + " " + method.getName() + "() {" + lineSeparator);
            stringBuilder.append("try {" + lineSeparator);
            stringBuilder.append("myInvocationHandler.invoke(this, m3, (Object[]) null);" + lineSeparator);
            stringBuilder.append("} catch (Throwable var3) {" + lineSeparator);
            stringBuilder.append("} " + lineSeparator);
            stringBuilder.append("} " + lineSeparator);
        }

        stringBuilder.append("}" + lineSeparator);

        return stringBuilder.toString();

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        System.out.println("do something before invoke target's method");
        method.invoke(target, args);
        System.out.println("do something after invoke target's method");
        return null;
    }
}
