# spring第一篇
* BOP/AOP
    * 在spring中提出BOP的概念，将对象都抽象为一个Bean，通过IOC容器将这些Bean都管理起来
    * IOC控制反转，将Bean的实例化的控制权交给IOC容器，代码只用来描述Bean与Bean之间的关系。Spring通过BeanFactory接口定义Bean的获取方式等行为
    * DI，在IOC容器对Bean实例化的过程中，不在自己维护的代码内容通过getBean的方式进行查找，而是在容器初始化的时候，依赖容器中提供的Bean注入
    * 注入方式:构造方法注入，set方法注入
    * AOP面向切面编程
    > thread1------method1-----method2.1-----method3-----  
    thread2------method1-----method2.2-----method3-----  
    thread3------method1-----method2.3-----method3-----  
      
        * 如上图所示，每个线程执行流程都是method1-method2.X-method3，这种情况下我们定义一个规则，统一定义method2.x的调用位置。然后将method1和method3的代码提取到公共位置，这个时候编写代码的程序员不在考虑method1和method3的实现，而是专心负责method2.x的实现，将来在运行时将这些代码连接起来
        * 规则:就是切面
        * 切入点:就是那些约定了的方法入口的位置        
* spring各个模块
    * spring-beans/spring-core/spring-context/spring-expression
        * beans/core是核心的ioc/di的实现模块
        * context模块扩展了IOC容器，为Bean增加了生命周期控制/事件体系等
        * spring-expression是EL表达式的扩展模块
    * orm/jdbc/oxm/jms/transactions
        * jdbc用来简化数据库的连接访问
        * tx事务控制模块
        * orm是mysql数据与应用中实体的mapping关系的实现模块
        * jms消息机制
        * oxm提供javabean与xml互相转换
    * web/webmvc/webflux/websocket
        * web提供最基础的web支持，建立在核心容器之上，通过servlet或者listener方式来初始化ioc容器
    * aspects/aop/instrument
        * aop是核心的aop的实现模块，借助动态代理功能实现前置/后置/异常/返回/环绕等通知
        * aspects继承至aspectj框架
        * instrument是动态代理的字节码重组的支持类库
    * messaging/test    
        * messaging是spring4开始新增模块
        * test模块提供，在应用不发布的情况下调用代码进行测试
        