

* 使用Example查询会有什么问题
    * 会在mapper文件中，无法清晰到看到哪些需要建立索引列
* 问题：当mapper文件和注解共存的情况下，会执行那个
    * 在使用xml文件配置Configuration的情况下
        * 会调用XMLMapperBuilder#parse()方法
            * configurationElement(parser.evalNode("/mapper"))
                * buildStatementFromContext(context.evalNodes("select|insert|update|delete"))
                * 加载了xml配置文件中的所有select|insert|update|delete的sql语句
            * bindMapperForNamespace()
                * configuration.addMapper(boundType)
                    * MapperAnnotationBuilder#parse
                        * for (Method method : methods) {
                            * parseStatement(method)
                                * SqlSource sqlSource = getSqlSourceFromAnnotations(method, parameterTypeClass, languageDriver)
                                * 如果slqSource不为空就会执行assistant.addMappedStatement(....),最终执行configuration.addMappedStatement(statement)
        * 查看Configuration类的mappedStatements成员变量
            * protected final Map<String, MappedStatement> mappedStatements = new StrictMap<MappedStatement>("Mapped Statements collection");
            * 由于数据类型是StrictMap，当添加重复的key时，会报错。不重复可以正常存储
    * 使用非xml形式配置Configuration的情况下
        * Configuration#addMapper
            * MapperAnnotationBuilder#parse
                * loadXmlResource()
                    * 该方法，将Mapper接口的全路径类名称，将.替换为/，查找xml资源文件，进行加载
                    * 加载过程可以参考使用xml配置的加载过程
                * for (Method method : methods) {
                    * 循环加载当前Type的方法上面带有sqlSource的方法，加入statement集合
    * 结论  
    > 在使用mybatis时，如果同时使用注解和mapper.xml方式，一定要保证同一个statement只能声明xml或者注解中的一种，多个statement可以混用
    * another question 非xml加载的时候，调用了xml加载的逻辑，xml加载过程中，已经加载过注解的statement，那非xml加载注解statement的时候会不会报错
    > 答案肯定是不会报错啦，为什么呢。mybatis的configuration中有一个loadedResources属性  
    protected final Set<String> loadedResources = new HashSet<String>()  
    可以认真读下源码，每次加载完xml或者mapper文件的时候，都会将加载的resource写入该set集合，下次加载之前先判断，按需加载  
    another question 多线程加载的时候，这个判断是不是会出问题？  
    确实会有问题，但是我们只要控制一个application启动过程中，只有一个SqlSessionFactory.build()调用，就没有问题喽        
                