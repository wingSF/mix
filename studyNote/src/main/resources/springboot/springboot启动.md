1.springboot的启动方式
    public static void main(String[] args) {
    		SpringApplication.run(PrimarySources.class, args);
    }

    跟踪源码，上述过程可拆分为
    new SpringApplication(primarySorce).run(args)

    所以启动可以拆分为俩个过程
    new:
        public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
        		this.resourceLoader = resourceLoader;
        		Assert.notNull(primarySources, "PrimarySources must not be null");
        		this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
        		this.webApplicationType = deduceWebApplicationType();
        		setInitializers((Collection) getSpringFactoriesInstances(
        				ApplicationContextInitializer.class));
        		setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
        		this.mainApplicationClass = deduceMainApplicationClass();
        }

        上述代码中
        resourceLoader暂时不明确目的
        mainApplicationClass 是项目的启动Class对象
        primarySources中会有 mainApplicationClass
        webApplicationType会根据deduceWebApplicationType的结果分为三种 NONE 、SERVLET 、REACTIVE
        setInitializers和setListeners下层都会调用同一个方法
            getSpringFactoriesInstances
                SpringFactoriesLoader.loadFactoryNames(type, classLoader));
                    loadSpringFactories(classLoader).getOrDefault(factoryClassName, Collections.emptyList())
                    其中loadSpringFactories是核心，会加载jar下的META-INF\spring.factories文件中的配置项
                    加载过程中，第一次全部初始化，后续读取cache
                List<T> instances = createSpringFactoriesInstances(type, parameterTypes,classLoader, args, names);
                    创建Beand对象
                AnnotationAwareOrderComparator.sort(instances);
                    此处在返回的时候会有一个排序过程

    run:
        configureHeadlessProperty();
            配置headless模式
        SpringApplicationRunListeners listeners = getRunListeners(args);
        listeners.starting();
            listener.onApplicationEvent
            调用starting之后，找到监听starting event的listener，然后调用onApplicationEvent方法
        context = createApplicationContext();
        prepareContext(context, environment, listeners, applicationArguments,printedBanner);
            applyInitializers(context);
                for (ApplicationContextInitializer initializer : getInitializers()) {
                    ...
                    initializer.initialize(context);
                    调用initalize方法
                }
        refreshContext(context);
            refresh(context);
                ((AbstractApplicationContext) applicationContext).refresh();

至此，启动结束。中间还有很多细节例如，bean初始化，事件发布等.....

https://blog.csdn.net/column/details/18708.html?&page=2
    简直不要太丰富，感觉扣开了spring屋子的一个角落，全是亮金金的宝石啊.....
    后续还得不断补充
https://blog.csdn.net/zl1zl2zl3/article/details/79765725
