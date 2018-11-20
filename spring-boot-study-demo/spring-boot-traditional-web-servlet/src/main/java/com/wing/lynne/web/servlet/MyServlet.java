package com.wing.lynne.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 核心点，继承HttpServlet，重写doGet方法
 * 使用@WebServlet注解，注册servlet，指定映射地址/my/servlet
 * 注册的前提是，在Application中的ServletCompentScan的注解，指定扫描路径
 */
@WebServlet(urlPatterns = "/my/servlet")
public class MyServlet extends HttpServlet {


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("hello,world");
    }

}
