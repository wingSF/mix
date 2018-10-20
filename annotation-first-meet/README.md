# annotation初认识
* Q1：注解是什么东西
* Q2：注解有什么作用
* Q3：如何工作的
* Q4：适用场景
* Q5：平时我们加注解都是如何工作的（包括自定义注解）

## 注解是什么
* 注解是元数据的一种表达形式，不属于程序的一部分，但是可以为程序提供数据。
* 注解不会对标注的程序产生直接影响
* 像 '@Entity' 这样的就是注解，'@'是标识符，标识符后面的Entity是注解的名称
* 注解可以包含自定义元素，并且每个元素都会有value值，如下所示
```java
@Author(
    name="wing",
    date="17/10/2018"
)
class MyClass{
}
```
* 注解可以包含自定义元素，如果只写value不写元素名称，默认元素名称是value，如下所示
```java

class MyClass {
    
    @SuppressWarnings("unchecked")
    void myMethod() {
    }
}
```

## 注解有什么作用
* Information for the compiler
    * 注解可以用来发现错误或者压制警告
        * @Overried用来表示一个方法，提示编译器，如果父类没有类似的方法，就抛出一个错误
* Compile-time and deployment-time processing
    * 编译期间或者部署期间被解析，来生成代码
* Runtime processing
    * 运行时可以使用的注解
    
> 官方的注解用途为以上三个方面，而开发经常只用运行时这个过程。在人脑的开发下，出现了许多奇技淫巧，如标记注解@Marker之类。
本身该注解不包含任何metadata，仅仅是一个标记。
    
    
## 如何工作的
* 此处感觉翻译会影响阅读效果，原文添加.重点阅读 [DZone](https://dzone.com/articles/how-annotations-work-java)
```text
Annotations are only metadata and they do not contain any business logic.
Tough to digest but true.
If annotations do not contain the logic than someone else must be doing something and that someone is consumer of this annotation metadata.
Annotations only provide some information about the attribute (class/method/package/field) on which it is defined.
Consumer is a piece of code which reads this information and then preforms necessary logic.
```

* annotation解析之反射
```java
class BusinessLogic {
    
    public void m(){
        
        Class businessLogicClass = BusinessLogic.class;
        
        for(Method method:BusinessLogic.getMethods()){
            
            YourAnnotation yourAnnotation =  method.getAnnotation(YourAnnotation.class);
            
            if(yourAnnotation!=null){
                
                System.out.println("method name :"+method.getName());
                System.out.println("Author :"+yourAnnotation.author());
                System.out.println("Priority :"+yourAnnotation.priority());
                System.out.println("Status :"+yourAnnotation.status());
                
            }
        }
    }
}
```

## 适用场景
我们的常见使用只针对运行时的注解，一般是用来初始化一些元数据。初始化的方式有俩种一种是xml，一种是注解。注解优于xml的好处是离代码足够近。关于适用场景，个人感觉实在人为，例如@Marker这样的用法，就是仁者见仁，智者见智了。

## 常见注解的运行机制
* spring相关的注解
    * @ComponentScan @Component @Controller @Service @Dao @Repository ....
    > 本来只是想找几个spring的常用注解分析下运行和加载的过程，结果发现似乎敲开了spring core的核心，由于时间有限，且spring需要独立开篇，所以此处只演示加载过程，如想获得更加详细的内容参见如下附件，或者等我把spring core吃透了再写一篇，再或者年底看小马哥的spring boot编程思想，他写的绝对叼炸天。虽然我问他问题，从来不回复我。。。
    * 相关文档
        * [spring annotation-based container configuration](https://docs.spring.io/spring/docs/5.1.1.RELEASE/spring-framework-reference/core.html#beans-annotation-config);
        * [spring classpath scanning and managed components](https://docs.spring.io/spring/docs/5.1.1.RELEASE/spring-framework-reference/core.html#beans-classpath-scanning);
        * [spring annotation programming model](https://github.com/spring-projects/spring-framework/wiki/Spring-Annotation-Programming-Model)
    * ApplicationContext
        * 该接口为应用提供核心的配置信息，详见该类的javadoc注释
    * AnnotationConfigApplicationContext
        * 该类是注解加载的核心类，可查看类的层级结构（IDEA的功能）
    * 代码请参考[spring auto detect component and scanning exanple](http://websystique.com/spring/spring-auto-detection-autowire-component-scanning-example-with-annotations/)
    * 代码示例
```java
public class AppMain {
 
    public static void main(String args[]){
        
        /**
        * 第一种方式，指定config类，类上面使用@ComponentScan注解，注解的标签上指定要扫描的包路径
        * 
        * config类示例
          @Configuration
          @ComponentScan(basePackages = "com.websystique.spring")
          public class AppConfig {
           
          }
        * 
        */
        AbstractApplicationContext  context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        
        /**
        * 第二种方式，直接指定扫描路径
        * 
        */
        AnnotationConfigApplicationContext  context = new AnnotationConfigApplicationContext();
        //指定要扫描的包路径
        context.scan("com.websystique.spring"); 
        context.refresh();
        
        EmployeeService service = (EmployeeService) context.getBean("employeeService");
 
        /*
         * Register employee using service
         */
        Employee employee = new Employee();
        employee.setName("Danny Theys");
        service.registerEmployee(employee);
         
        context.close();
    }
}
```
> 以上扫描只会把被@Component注解标注的类加载到context里面，由于@Controller，@Service等注解被@Component修饰，所以也会被加载。但是消费@Controller和@Service注解的类，不是这个地方。这个体系太大了，先理解到这里，后续更新吧

> 再提一个统一事务的实现，是依赖aop的支持，aspect也是一种注解的consumer方式
## 参考资料
* [The Java Tutorials From Oracle](https://docs.oracle.com/javase/tutorial/java/annotations/index.html)
* [DZone](https://dzone.com/articles/how-annotations-work-java)
  

