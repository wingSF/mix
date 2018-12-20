# spring第二篇
* BeanFactory bean工厂的顶层接口
    * DefaultListableBeanFactory 
        * bean工厂的默认实现类
    * BeanDefinition
        * 配置文件解析的最终结果
    * BeanDefinitionReader
        * 解析bean配置的reader对象
* IOC容器初始化的过程
    * AbstractApplicationContext中的refersh()方式是容器启动的方法，方法中定义了ioc容器启动的过程
    * 核心过程包括以下步骤
        * 资源定位
        * 加载配置文件，转化BeanDefinition
        * 注册到IOC容器中
        * 显示调用getBean实例化Bean对象
    * beanFactory & factoryBean
        * beanFactory是ioc容器的抽象接口
        * factoryBean是为ioc容器管理的一个bean，通过这个bean可以获取相对应的bean
    * BeanPostProcessor
        * bean的后置处理器
        * before & after
* 核心对象
    * `ResourcePatternResolver`和`XmlBeanDefinitionReader`资源加载
    * `AbstractRefreshableConfigApplicationContext#configLocations`配置文件的位置
    * `DefaultListableBeanFactory`实际执行ioc容器启动的类
    * `BeanDefinition`bean的描述
    * `DefaultListableBeanFactory#beanDefinitionMap`ioc容器中beandefinition的存放位置
    * `BeanWrapper`原声bean的包装对象
         

> 在spring框架中的bean，getName获取的可能是原始bean的名称，但实质是一个代理对象,这个时候如果要对这个bean进行一些原始注解的判断，可以借用`AopProxyUtils#getSingletonTarget`方法获取原始数据，但是在反射调用的过程中，由于要保证代理方法的执行，要使用代理对象
    