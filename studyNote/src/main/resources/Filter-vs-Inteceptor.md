# 过滤器 vs 拦截器
* 还记得被这个面试支配的感觉嘛～～～

* filter(过滤器)
    * `javax.servlet.Filter`
        * 从包路径可以看出来是servlet下的一个filter组件
    * 接口定义
        ```java 
        package javax.servlet;
        import java.io.IOException;
        
        public interface Filter {
            default void init(FilterConfig filterConfig) throws ServletException {}

            void doFilter(ServletRequest var1, ServletResponse var2, FilterChain var3) throws IOException, ServletException;

            default void destroy() {}
        }
        ```
        * 从接口定义看，核心方法是doFilter方法，方法内对request和response进行操作，然后交给filterchain去执行下一个
    * 本质是方法调用，责任链思想
* interceptor(拦截器)
    * `org.springframework.web.servlet.HandlerInterceptor`
        * 从包路径可以看出来是springframewrk下的一个接口
        * 但是包路径中含有servlet，说明与servlet有关，可以理解为对servlet技术的一种补充
    * 接口定义
        ```java
        package org.springframework.web.servlet;

        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import org.springframework.lang.Nullable;

        public interface HandlerInterceptor {
            default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                return true;
            }

            default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
            }
        
            default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
            }
        }
        ```
        * 从接口定义看，核心方法有`preHandle`,`postHandle`,`afterCompletion`
    * 本质是根据用户的配置(xml or annotation)生成代理对象，通过代理对象对方法request和response进行处理
        * 通过对核心方法的实现，可以更加简单的实现功能增强