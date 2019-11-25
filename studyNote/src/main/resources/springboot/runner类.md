# [runner类](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/html/spring-boot-features.html#boot-features-command-line-runner)
* 原文
    ```text
    If you need to run some specific code once the SpringApplication has started, you can implement the ApplicationRunner or CommandLineRunner interfaces. Both interfaces work in the same way and offer a single run method, which is called just before SpringApplication.run(…​) completes.
    ```
* ApplicationRunner
    ```text
    whereas the ApplicationRunner uses the ApplicationArguments interface discussed earlier
    ```
* CommandLineRunner
    ```text
    The CommandLineRunner interfaces provides access to application arguments as a simple string array
    ```
    ```java
    @Component
    public class MyBean implements CommandLineRunner {
    
        public void run(String... args) {
            // Do something...
        }
    
    }
    ```
* 启动顺序控制
    ```text
    If several CommandLineRunner or ApplicationRunner beans are defined that must be called in a specific order, you can additionally implement the org.springframework.core.Ordered interface or use the org.springframework.core.annotation.Order annotation
    ```