package com.wing.lynne.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping("login")
    public ModelAndView login(@RequestParam(value = "username", required = false) String username,
                              @RequestParam(value = "password", required = false) String password,
                              HttpServletRequest request) {

        boolean loginResult = "root".equals(username) && "root".equals(password);

        if (loginResult) {

            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("password", password);

            return new ModelAndView("list");
        } else {
            return new ModelAndView("login");
        }
    }

}
