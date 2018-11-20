package com.wing.lynne.web.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 传统webservlet的异步实现，要点如下
 * 注解修改asyncSupport默认值false为true
 * 通过HttpServletRequest#startAsync获取异步的AsyncContext
 * 通过AsyncContext#start方法，执行业务逻辑
 * 一定要手动调用AsyncContext的complete方法，因为他是异步阻塞的实现，如果不调用complete就会一直等待，直到超时
 */
@WebServlet(urlPatterns = "/async/servlet",asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestThreadName = Thread.currentThread().getName();

        AsyncContext asyncContext = req.startAsync();

        asyncContext.start(() -> {
            try {
                String handleRequestThreadName = Thread.currentThread().getName();
                resp.getWriter().write(requestThreadName);
                resp.getWriter().println();
                resp.getWriter().write("hello,world");
                resp.getWriter().println();
                resp.getWriter().write(handleRequestThreadName);

                asyncContext.complete();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

//        如果将下面注释打开，注释掉上面代码中的相同代码段，将会导致在当前线程内，提前结束异步线程的逻辑执行，可能会导致业务执行不完全
//        asyncContext.complete();
    }

}
