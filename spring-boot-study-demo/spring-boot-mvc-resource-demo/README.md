# springboot resource
> 鉴于多次在springboot资源加载上出现问题，特定创建该项目用来记录资源显示过程 
* 注意:这里只完成静态资源加载过程，详细的登录拦截功能暂时没做相应实现 
* 先介绍我这个项目里面用到的配置方式
    * 渲染html
        * 先配置WebConfig类，增加LoginInterceptor来拦截/admin/**的请求
        * AdminController处理/admin/**的请求，返回视图名称
        * thymeleaf根据视图名称和application.properties中的`spring.thymeleaf.prefix``spring.thymeleaf.suffix`俩项配置，找到对应的html文件进行渲染
    * html加载静态资源
        * 通过application.properties中的`spring.mvc.static-path-pattern=/resources/**`项，严格控制什么样的请求是资源请求
        * 所有页面加载静态资源都必须写成`href="/resources/css/style.css`这样的
            * 特别注意 必须是 '/'开头 'resource'紧随气候，再跟资源路径
            * 在不重写静态资源处理的情况下，默认静态资源路径参见`org.springframework.boot.autoconfigure.web.ResourceProperties`的`CLASSPATH_RESOURCE_LOCATIONS`属性
        * 上述俩步是静态资源加载的一种方式，当然还有别的方式
        * 别的方式
            * 自己重写resourceHandle，这个时候默认静态路径不再生效，需要自行控制
            * 详情参见`com.wing.lynne.config.WebConfig#addResourceHandlers`
            * 注意使用这种方式，请注释掉无用配置，即application.properties中的`spring.mvc.static-path-pattern`配置