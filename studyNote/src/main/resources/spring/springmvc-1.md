# springmvc第一篇

## 初始化过程
* DispatcherServlet实现了FrameworkServlet
* FrameworkServlet在会在ioc容器启动的时候初始化，初始化的时候会调用onRefresh方法或者收到初始化的事件，最终调用核心方法onRefresh
* onRefresh的核心逻辑，就是初始化springmvc的九大组件
    * initMultipartResolver(context);
    * initLocaleResolver(context);
    * initThemeResolver(context);
    * initHandlerMappings(context);
    * initHandlerAdapters(context);
    * initHandlerExceptionResolvers(context);
    * initRequestToViewNameTranslator(context);
    * initViewResolvers(context);
    * initFlashMapManager(context);
* 初始化中的核心 HandleMapping/HandleAdapter/ViewResolver
    * HandleMapping
        * 将所有的HandleMapping及其子类，准备后续的getBean初始化
        * RequestMappingHandleMapping在初始化过程
            * 调用afterPropertiesSet
                * 调用initHandleMethod
                    * 判断isHandle(实质就是判断有@Controller或者有@RequestMapping)
                    * detectHandlerMethods进行注册
                        * this.mappingLookup.put(mapping, handlerMethod);
                        * this.urlLookup.add(url, mapping);
                        * this.nameLookup.put(name, newList);
                        * this.registry.put(mapping, new MappingRegistration<>(mapping, handlerMethod, directUrls, name));
                        > 以上提到的对象中 handleMethod就是controller中的某个被requestmapping表示的方法  
                        name是springBean的simpleName+'#'+方法名称组成的  
                        simpleName是全类名中的大写字母  
                        ex:BusinessController中的sendMessage1的方法，对应的name就是'BC#sendMessage1'
                        * registerHandlerMethod
                            * createHandlerMethod
                                * 在`newHandleMethod`的内部调用了`initMethodParameters`
                                * for循环中的俩行代码，将参数封装为`MethodParam`对象，并设置其paramType属性值
                    * 注册到`AbstractHandleMethodMapping`的`mappingRegistry`成员变量里面


## 执行过程
* DispatcherServlet的doDispatch
* 根据request获取HandlerExecutionChain
    * 获取过程根据url去注册的urlmapping中寻找HandleMethod
        * 详细代码可以参考`AbstractHandlerMethodMapping#lookupHandlerMethod`
        * 具体过程，根据url找，如果没找到，就拿所有，然后排序，然后根据多个condition去匹配，找到最合适的
        * condition可查看`RequestMappingInfo`中的condition成员变量
    * 根据请求地址，获取拦截器列表，组装HandlerExecutionChain对象返回
* 获取HandleAdapter
    * `HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());`
* 通过HandleAdapter调用对应的方法，获取ModelAndView
    * `mv = ha.handle(processedRequest, response, mappedHandler.getHandler());`
    * `AbstractHandlerMethodAdapter#handleInternal`
    * `invokeHandlerMethod`
    * `invocableMethod.invokeAndHandle(webRequest, mavContainer);`
    * `invokeForRequest`
    * `getMethodArgumentValues`完成参数绑定
    * `doInvoke`完成handleMethod的调用，同时获取returnValue对象
    * `this.returnValueHandlers.handleReturnValue....`处理响应对象
        * `RequestResponseBodyMethodProcessor#handleReturnValue`,该方法第一步将mavContainer.setRequestHandled(true);后续`mavContainer.isRequestHandled()`判断为true，不会再走ModelAndView解析
        * `ViewNameMethodReturnValueHandler#handleReturnValue`该方法用来解析页面,后续执行过程中`mavContainer.isRequestHandled()`将会是false，继续走View解析
* 通过viewResolver解析mv，然后响应给客户端（spelView）
    * `processDispatchResult`
    * `view.render(mv.getModelInternal(), request, response);`处理页面转换
