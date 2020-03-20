package com.wing.controller.user;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/wing")
public class UserController {


    @GetMapping("/findByParam")
    @ResponseBody
    public UserResponse findUserParam(int userId) {
        UserResponse userResponse = new UserResponse();
        return userResponse;
    }


    @GetMapping("/findByBody")
    @ResponseBody
    public UserResponse findUserByBody(@RequestBody UserRequest request) {
        UserResponse userResponse = new UserResponse();
        return userResponse;
    }
}
